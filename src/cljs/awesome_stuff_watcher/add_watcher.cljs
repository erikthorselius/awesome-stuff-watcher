(ns awesome-stuff-watcher.add-watcher
  (:require [json-html.core :refer [edn->hiccup json->hiccup]]
            [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET POST]]))
(defn- search [doc] [:input {:type        "text"
                             :placeholder "Sök..."
                             :on-input   #(swap! doc assoc :q (-> % .-target .-value))}])
(defn- item-li [{:keys [img title price url]}] [:a {:href url}
                                                [:div
                                                 [:img {:src img :align "left"}]
                                                 [:h3 title]
                                                 [:p price]]])
(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li [item-li item]])])

(defn- preview [doc] (do (GET "/preview" {:params          @doc
                                          :handler         #(swap! doc assoc :preview (:data %))
                                          :error-handler   (fn [r] (prn r))
                                          :response-format :json
                                          :keywords?       true})
                         [lister (:preview @doc)]))
(defn page []
  (let [doc (atom {})]
    (fn []
      [:div
       [:div.page-header [:h1 "Lägg till bevakare"]]
       [:form {:action "/"}
        [search doc]
        [:input {:type "submit" :value "Submit"}]
        [:li (preview doc)]]
       [:hr]
       [:h1 "Document State"]
       [edn->hiccup @doc]])))
