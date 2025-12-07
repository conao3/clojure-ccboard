(ns conao3.claude-code-dashboard.frontend.core
  (:require
   ["@apollo/client" :as apollo]
   ["@apollo/client/react" :as apollo.react]
   [reagent.dom.client :as reagent.dom.client]))

(enable-console-print!)

(defonce apollo-client
  (apollo/ApolloClient. #js {:link (apollo/HttpLink. #js {:uri "/api/graphql"})
                             :cache (apollo/InMemoryCache.)}))

(def projects-query
  (apollo/gql "query Projects { projects { edges { node { id name rawName } } } }"))

(defn ProjectList []
  (let [result (apollo.react/useQuery projects-query)
        loading (.-loading result)
        error (.-error result)
        data (.-data result)]
    (cond
      loading [:p "Loading..."]
      error [:p (str "Error: " (.-message error))]
      :else [:ul
             (for [edge (-> data .-projects .-edges)]
               (let [node (.-node edge)]
                 [:li {:key (.-id node)} (.-name node)]))])))

(defn App []
  [:> apollo.react/ApolloProvider {:client apollo-client}
   [:div
    [:h1 "Claude Code Dashboard"]
    [:f> ProjectList]]])

(defonce root (-> js/document (.getElementById "app") reagent.dom.client/create-root))

(defn ^:dev/after-load start []
  (reagent.dom.client/render root [App]))
