import { writeFile } from "node:fs/promises";
import { resolve, dirname } from "node:path";
import { fileURLToPath } from "node:url";
import { getFileTokens } from "@adobe/spectrum-tokens";

const __dirname = dirname(fileURLToPath(import.meta.url));
const ROOT_DIR = resolve(__dirname, "../..");
const OUTPUT_PATH = resolve(ROOT_DIR, "resources/public/css/spectrum-colors.css");

const THEME = "dark";
const TOKEN_FILES = [
  "color-palette.json",
  "semantic-color-palette.json",
  "color-aliases.json",
];

const stripSuffix = (name) => name.replace(/-color$/, "").replace(/-default$/, "");
const resolveRef = (val) => val.replace(/\{([^}]+)\}/g, (_, ref) => `var(--color-${stripSuffix(ref)})`);

const cssLines = ["@theme {"];

for (const file of TOKEN_FILES) {
  cssLines.push("");
  cssLines.push(`  /* ${file} */`);
  const tokens = await getFileTokens(file);
  for (const [key, value] of Object.entries(tokens)) {
    const rawValue = value.value ?? value.sets?.[THEME]?.value;
    if (rawValue != null && typeof rawValue !== "object") {
      cssLines.push(`  --color-${stripSuffix(key)}: ${resolveRef(String(rawValue))};`);
    }
  }
}

cssLines.push("}");
cssLines.push("");

await writeFile(OUTPUT_PATH, cssLines.join("\n"), "utf8");

console.log(`Generated ${OUTPUT_PATH} (${cssLines.length - 2} colors)`);
