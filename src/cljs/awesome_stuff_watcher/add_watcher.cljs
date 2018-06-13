(ns awesome-stuff-watcher.add-watcher
  (:require [json-html.core :refer [edn->hiccup]]
            [reagent.core :as reagent :refer [atom]]
            [reagent-forms.core :refer [bind-fields init-field value-of]]))
(defn row [label input]
  [:div.row
   [:div.col-md-2 [:label label]]
   [:div.col-md-5 input]])

(defn input [label type id]
  (row label [:input.form-control {:field type :id id}]))

(defn friend-source [text]
  (filter
    #(-> % (.toLowerCase %) (.indexOf text) (> -1))
    ["Alice" "Alan" "Bob" "Beth" "Jim" "Jane" "Kim" "Rob" "Zoe"]))
(def blocket [:div "Blocket"])
(def systembolaget [:div "Systembolaget"])
(defn extra-fields [watcher] (get {:blocket blocket
                                   :systembolaget systembolaget} watcher))
(def form-template
  [:div
   [:div.form-group
    [:label "Bevaking"]
    [:select.form-control {:field :list :id :watcher}
     [:option {:key :blocket} "Blocket"]
     [:option {:key :happyride} "Happy Ride"]
     [:option {:key :systembolaget} "Systembolaget"]]]
   (input "Sök text" :text :watcher.q)
   [:div.row
    [:div.col-md-2]
    [:div.col-md-5
     [:div.alert.alert-danger
      {:field :alert :id :errors.q}]]]
   (input "Notifiering" :text :watcher.notification)])

(defn page []
  (let [doc (atom {})]
    (fn []
      [:div
       [:div.page-header [:h1 "Lägg till bevakare"]]

       [bind-fields
        form-template
        doc
        (fn [[id] value {:keys [weight-lb weight-kg] :as document}]
          (cond
            (= id :weight-lb)
            (assoc document :weight-kg (/ value 2.2046))
            (= id :weight-kg)
            (assoc document :weight-lb (* value 2.2046))
            :else nil))
        (fn [[id] value {:keys [height weight] :as document}]
          (when (and (some #{id} [:height :weight]) weight height)
            (assoc document :bmi (/ weight (* height height)))))]

       [:button.btn.btn-default
        {:on-click
         #(if (empty? (get-in @doc [:person :first-name]))
            (swap! doc assoc-in [:errors :first-name]"first name is empty"))}
        "save"]
       [:hr]
       [:h1 "Document State"]
       [edn->hiccup @doc]])))