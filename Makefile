.PHONY: all
all:

.PHONY: repl
repl:
	clj -M:dev:repl

.PHONY: update
update:
	clojure -Sdeps '{:deps {com.github.liquidz/antq {:mvn/version "RELEASE"}}}' -M -m antq.core --upgrade --force

.PHONY: build-css
build-css:
	pnpm exec postcss resources/public/css/main.css -o resources-dev/public/dist/css/main.css

.PHONY: watch-css
watch-css:
	pnpm exec postcss resources/public/css/main.css -o resources-dev/public/dist/css/main.css --watch

.PHONY: release-frontend
release-frontend:
	pnpm exec shadow-cljs release frontend

.PHONY: release-backend
release-backend:
	pnpm exec shadow-cljs release backend

.PHONY: release
release: release-frontend release-backend

.PHONY: test-frontend
test-frontend:
	pnpm exec shadow-cljs compile test-frontend

.PHONY: test-backend
test-backend:
	pnpm exec shadow-cljs compile test-backend

.PHONY: test
test: test-frontend test-backend

.PHONY: run-backend
run-backend:
	node resources-dev/backend/main.js

.PHONY: clean
clean:
	rm -rf target .shadow-cljs .cpcache resources-dev/public/dist resources-dev/backend
