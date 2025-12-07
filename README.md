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
