(ns dev
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str])
  (:import [io.undertow.server HttpServerExchange]))

(defn api-route? [^HttpServerExchange exchange _config]
  (let [path (.getRequestPath exchange)]
    (str/starts-with? path "/api")))

(defn- valid-graphql-schema? [schema-path]
  (let [result (shell/sh "node" "-e"
                         (str "const fs = require('fs');"
                              "const { buildSchema } = require('graphql');"
                              "const schema = fs.readFileSync('" schema-path "', 'utf-8');"
                              "try { buildSchema(schema); }"
                              "catch (e) { console.error(e.message); process.exit(1); }")
                         :err :out)]
    {:valid (zero? (:exit result))
     :error (:err result)}))

(defn validate-graphql-schema
  {:shadow.build/stage :compile-prepare}
  [build-state]
  (let [status (valid-graphql-schema? "resources/schema.graphql")]
    (when-not (:valid status)
      (Thread/sleep 3000)
      (throw (ex-info "Invalid GraphQL schema" status)))
    build-state))
