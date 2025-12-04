(ns conao3.claude-code-dashboard.backend.core
  (:require
   ["@apollo/server" :as apollo]
   ["@apollo/server/standalone" :as apollo.standalone]
   ["fs" :as fs]
   ["path" :as path]))

(def type-defs
  (fs/readFileSync (path/join js/__dirname ".." ".." "resources" "schema.graphql") "utf-8"))

(def resolvers
  #js {:Query #js {:hello (fn [] "Hello from Apollo Server!")}})

(defn main [& _args]
  (-> (apollo/ApolloServer. #js {:typeDefs type-defs :resolvers resolvers})
      (apollo.standalone/startStandaloneServer #js {:listen #js {:port 4000}})
      (.then (fn [res]
               (println (str "Server ready at " (.-url res)))))))
