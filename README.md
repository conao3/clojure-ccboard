# clojure-claude-code-dashboard

Claude Code Dashboard - ClojureScript frontend with Apollo GraphQL backend.

## Development

### Quick Start

```bash
make server          # Terminal 1: Start shadow-cljs server
make watch           # Terminal 2: Start watch builds
make watch-css       # Terminal 3: Watch CSS changes
make run-backend     # Terminal 4: Start backend server
```

Then open http://localhost:8000/

## Make Targets

| Target | Description |
|--------|-------------|
| `make server` | Start shadow-cljs server |
| `make watch` | Start watch builds for frontend, backend, and tests |
| `make run-backend` | Start backend server |
| `make build-css` | Build CSS (generates spectrum colors and runs PostCSS) |
| `make watch-css` | Watch CSS changes |
| `make generate-spectrum-colors` | Generate `resources/public/css/spectrum-colors.css` from Spectrum Design Tokens |
| `make repl` | Start Clojure REPL |
| `make test` | Run all tests |
| `make test-frontend` | Run frontend tests |
| `make test-backend` | Run backend tests |
| `make release` | Build release for frontend and backend |
| `make release-frontend` | Build release for frontend |
| `make release-backend` | Build release for backend |
| `make update` | Update dependencies |
| `make clean` | Clean build artifacts |

## Ports

| Port | Description |
|------|-------------|
| 8000 | Frontend dev server (shadow-cljs dev-http) |
| 4000 | Backend API server (Express + Apollo Server) |
| 9100 | Frontend test runner |
| 9630 | shadow-cljs UI |

## Endpoints

- http://localhost:8000/ - Frontend application
- http://localhost:8000/admin/graphiql.html - GraphiQL IDE (dev only)
- http://localhost:8000/admin/apollo - Apollo Sandbox (dev only)
- http://localhost:8000/api/graphql - GraphQL API endpoint (proxied)
- http://localhost:9100/ - Frontend test runner
- http://localhost:9630/ - shadow-cljs UI

## Proxy

In development, requests to `http://localhost:8000/api/*` are proxied to `http://localhost:4000/api/*`.

## Styling with Spectrum Design Tokens

This project uses [Adobe Spectrum Design Tokens](https://github.com/adobe/spectrum-design-data) integrated with Tailwind CSS. The tokens are generated from `@adobe/spectrum-tokens` package and converted to Tailwind-compatible CSS custom properties.

### Color Sources

Colors are generated from three token files:

| File | Description |
|------|-------------|
| `color-palette.json` | Base colors (gray, blue, red, etc.) |
| `semantic-color-palette.json` | Semantic colors (accent, informative, negative, etc.) |
| `color-aliases.json` | Contextual aliases (background, content, border colors) |

### Usage Examples

#### Background Colors

```clojure
:div.bg-background-base      ; Base background
:div.bg-background-layer-1   ; Layer 1 background
:div.bg-background-layer-2   ; Layer 2 background
```

#### Text Colors

```clojure
:p.text-neutral-content   ; Default text
:p.text-accent-content    ; Accent text
:p.text-negative-900      ; Error text
```

#### Semantic Colors

```clojure
:div.bg-accent-900        ; Accent (blue)
:div.bg-informative-900   ; Informative (blue)
:div.bg-negative-900      ; Negative/Error (red)
:div.bg-positive-900      ; Positive/Success (green)
:div.bg-notice-900        ; Notice/Warning (orange)
```

#### Base Colors

```clojure
:div.bg-gray-100   ; Gray scale (25-1000)
:div.bg-blue-900   ; Blue scale
:div.bg-red-900    ; Red scale
```

### Regenerating Colors

To regenerate `resources/public/css/spectrum-colors.css`:

```bash
make generate-spectrum-colors
```

The theme is set to "dark" by default. To change it, edit `THEME` in `tools/generate-spectrum-colors/index.mjs`.

### Color Reference

You can browse all available colors at the [Spectrum Design Tokens Viewer](https://opensource.adobe.com/spectrum-design-data/s2-tokens-viewer).
