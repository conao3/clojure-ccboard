(ns conao3.claude-code-dashboard.portfolio.core
  (:require
   [portfolio.ui :as ui]
   conao3.claude-code-dashboard.portfolio.navigation
   conao3.claude-code-dashboard.portfolio.messages
   conao3.claude-code-dashboard.portfolio.layout))

(defn ^:export start []
  (ui/start!
   {:config {:css-paths ["/dist/css/main.css"]
             :background/background-color "#1a1a2e"}}))
