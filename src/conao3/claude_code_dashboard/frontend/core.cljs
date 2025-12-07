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
      loading [:p.text-gray-500 "Loading..."]
      error [:p.text-red-900 (str "Error: " (.-message error))]
      :else [:ul.space-y-2
             (for [edge (-> data .-projects .-edges)]
               (let [node (.-node edge)]
                 [:li.p-3.bg-gray-100.rounded.text-gray-800.hover:bg-gray-200.cursor-pointer
                  {:key (.-id node)}
                  (.-name node)]))])))

(defn App []
  [:> apollo.react/ApolloProvider {:client apollo-client}
   [:div.min-h-screen.bg-gray-50.text-gray-900.p-8
    [:h1.text-3xl.font-bold.mb-6.text-blue-900 "Claude Code Dashboard"]
    [:f> ProjectList]]])

(defonce root (-> js/document (.getElementById "app") reagent.dom.client/create-root))

(defn ^:dev/after-load start []
  (reagent.dom.client/render root [App]))
