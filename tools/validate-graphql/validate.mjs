import { readFileSync, readdirSync, statSync } from 'fs';
import { join, extname } from 'path';
import { buildSchema, parse, validate } from 'graphql';

const SCHEMA_PATH = 'resources/schema.graphql';
const SRC_DIR = 'src';

// Extract GraphQL queries from ClojureScript files
// Looks for patterns like: (apollo/gql "query ...")
function extractQueries(content, filePath) {
  const queries = [];
  // Match (apollo/gql "...") patterns - handles multiline strings
  const regex = /\(apollo\/gql\s+"((?:[^"\\]|\\.)*)"\)/gs;
  let match;

  while ((match = regex.exec(content)) !== null) {
    // Unescape the string
    const query = match[1]
      .replace(/\\n/g, '\n')
      .replace(/\\"/g, '"')
      .replace(/\\\\/g, '\\');
    queries.push({ query, filePath, position: match.index });
  }

  return queries;
}

// Recursively find all .cljs files
function findClojureScriptFiles(dir) {
  const files = [];

  for (const entry of readdirSync(dir)) {
    const fullPath = join(dir, entry);
    const stat = statSync(fullPath);

    if (stat.isDirectory()) {
      files.push(...findClojureScriptFiles(fullPath));
    } else if (extname(entry) === '.cljs') {
      files.push(fullPath);
    }
  }

  return files;
}

function main() {
  console.log('GraphQL Schema Validation');
  console.log('=========================\n');

  // Load and parse schema
  console.log(`Loading schema from ${SCHEMA_PATH}...`);
  const schemaSource = readFileSync(SCHEMA_PATH, 'utf-8');

  let schema;
  try {
    schema = buildSchema(schemaSource);
    console.log('Schema parsed successfully.\n');
  } catch (error) {
    console.error('Failed to parse schema:');
    console.error(error.message);
    process.exit(1);
  }

  // Find and extract queries from ClojureScript files
  console.log(`Scanning ${SRC_DIR} for GraphQL queries...\n`);
  const cljsFiles = findClojureScriptFiles(SRC_DIR);

  let allQueries = [];
  for (const filePath of cljsFiles) {
    const content = readFileSync(filePath, 'utf-8');
    const queries = extractQueries(content, filePath);
    allQueries.push(...queries);
  }

  if (allQueries.length === 0) {
    console.log('No GraphQL queries found.');
    process.exit(0);
  }

  console.log(`Found ${allQueries.length} GraphQL queries.\n`);

  // Validate each query
  let hasErrors = false;

  for (const { query, filePath } of allQueries) {
    // Extract query name for display
    const nameMatch = query.match(/(?:query|mutation|subscription)\s+(\w+)/);
    const queryName = nameMatch ? nameMatch[1] : 'Anonymous';

    console.log(`Validating: ${queryName} (${filePath})`);

    try {
      const document = parse(query);
      const errors = validate(schema, document);

      if (errors.length > 0) {
        hasErrors = true;
        console.log('  FAILED:');
        for (const error of errors) {
          console.log(`    - ${error.message}`);
        }
      } else {
        console.log('  OK');
      }
    } catch (error) {
      hasErrors = true;
      console.log('  FAILED (parse error):');
      console.log(`    - ${error.message}`);
    }
  }

  console.log('\n=========================');
  if (hasErrors) {
    console.log('Validation FAILED');
    process.exit(1);
  } else {
    console.log('All queries validated successfully!');
    process.exit(0);
  }
}

main();
