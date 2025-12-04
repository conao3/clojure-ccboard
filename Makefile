.PHONY: all
all:

.PHONY: repl
repl:
	clj -M:dev:repl

.PHONY: update
update:
	clojure -Sdeps '{:deps {com.github.liquidz/antq {:mvn/version "RELEASE"}}}' -M -m antq.core --upgrade --force

.PHONY: release-frontend
release-frontend:
	npx shadow-cljs release frontend

.PHONY: release-backend
release-backend:
	npx shadow-cljs release backend

.PHONY: release
release: release-frontend release-backend

.PHONY: test-frontend
test-frontend:
	npx shadow-cljs compile test-frontend

.PHONY: test-backend
test-backend:
	npx shadow-cljs compile test-backend

.PHONY: test
test: test-frontend test-backend

.PHONY: run-backend
run-backend:
	node target/backend/main.js

.PHONY: clean
clean:
	rm -rf target .shadow-cljs .cpcache resources-dev/public/dist
