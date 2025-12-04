(ns conao3.claude-code-dashboard.backend.core
  (:require
   ["@apollo/server" :refer [ApolloServer]]
   ["@apollo/server/standalone" :refer [startStandaloneServer]]))

(def type-defs "
  type Query {
    hello: String
  }
")

(def resolvers
  #js {:Query #js {:hello (fn [] "Hello from Apollo Server!")}})

(defn main [& _args]
  (-> (ApolloServer. #js {:typeDefs type-defs :resolvers resolvers})
      (startStandaloneServer #js {:listen #js {:port 4000}})
      (.then (fn [res]
               (println (str "Server ready at " (.-url res)))))))
