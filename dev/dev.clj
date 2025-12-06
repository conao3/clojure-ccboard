(ns dev
  (:require
   [clojure.java.io :as io]
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [clojure.pprint :as clojure.pprint]
   [shadow.cljs.devtools.server.worker.impl :as shadow.cljs.worker.impl])
  (:import [io.undertow.server HttpServerExchange]))

(defn api-route? [^HttpServerExchange exchange _config]
  (let [path (.getRequestPath exchange)]
    (str/starts-with? path "/api")))

(defn- valid-graphql-schema? [schema-path]
  (let [result (shell/sh "node" "-e"
                         (str "const fs = require('fs');"
                              "const { buildSchema } = require('graphql');"
                              "const schema = fs.readFileSync('" schema-path "', 'utf-8');"
                              "buildSchema(schema);"))]
    {:valid (zero? (:exit result))
     :error (:err result)}))

(defn validate-graphql-schema
  {:shadow.build/stage :compile-finish}
  [build-state]
  (clojure.pprint/pprint [:build-state (select-keys build-state [:compile-finish
                                                                 :mode
                                                                 ;; :output
                                                                 :repl-state
                                                                 :shadow.build.data/build-state
                                                                 ;; :shadow.build/build-info
                                                                 :shadow.build/mode
                                                                 :shadow.build/stage
                                                                 ;; :sources
                                                                 :shadow.build/marker
                                                                 :worker-info
                                                                 ])])
  (let [status (valid-graphql-schema? "resources/schema.graphql")]
    (cond-> build-state
      (not (:valid status)) (shadow.cljs.worker.impl/build-failure (ex-info "Invalid schema" status))))
  ;; (let [schema-file (io/file "resources/schema.graphql")]
  ;;   (if-not (.exists schema-file)
  ;;     (do
  ;;       (println "WARNING: schema.graphql not found")
  ;;       build-state)
  ;;     (let [{:keys [valid error]} (valid-graphql-schema? schema-file)]
  ;;       (if valid
  ;;         build-state
  ;;         (-> build-state
  ;;             (warnings/add-warning {:warning :invalid-graphql-schema
  ;;                                    :msg (str "Invalid GraphQL schema:\n" error)}))))))
  )
