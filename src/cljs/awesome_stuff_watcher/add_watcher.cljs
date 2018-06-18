(ns awesome-stuff-watcher.add-watcher
  (:require [json-html.core :refer [edn->hiccup]]
            [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]))
(defn watcher-selector [doc]
  [:p
   [:label "Bevaking"]
   [:select.form-control {:placeholder "text" :on-change #(swap! doc assoc :watcher (.. % -target -value))}
    [:option " "]
    [:option {:value :blocket} "Blocket"]
    [:option {:value :happyride} "Happy Ride"]
    [:option {:value :systembolaget} "Systembolaget"]]])
(defn blocket [] [:h1 "Blocket"])
(defn happyride [] [:h1 "Happy ride"])
(defn systembolaget [] [:h1 "Systembolaget"])
(defn extra-fields [watcher] (case watcher
                               "blocket" [blocket]
                               "happyride" [happyride]
                               "systembolaget" [systembolaget]
                               [:p]))
(defn search [doc] [:input {:type        "text"
                            :placeholder "Sök..."
                            :on-change   #(swap! doc assoc :q (-> % .-target .-value))}])
(defn ajax [doc] (GET "/preview" {:params doc
                                  :error-handler (fn [r] (prn r))
                                  :response-format :json
                                  :keywords? true}))
(defn page []
  (let [doc (atom {})]
    (fn []
      [:div
       [:div.page-header [:h1 "Lägg till bevakare"]]
       [watcher-selector doc]
       [search doc]
       [extra-fields (:watcher @doc)]
       (println (ajax @doc))
       [:hr]
       [:h1 "Document State"]
       [edn->hiccup @doc]])))
