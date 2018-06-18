(ns awesome-stuff-watcher.watchers)
(def blocket {:url             "https://www.blocket.se"
              :mapper-fn       #(assoc {} :q (:q %))
              :search-defaults {:ca 11}})
(def systembolaget {:url             "https://www.systembolaget.se/sok-dryck"
                    :mapper-fn       #(assoc {} :searchquery (:q %))
                    :search-defaults {:fullassortment 1}})
(def happyride {:url             "https://www.happyride.se"
                :mapper-fn       #(assoc {} :search (:q %))
                :search-defaults {}})
(defn switch [watcher] (case watcher
                         "blocket" blocket
                         "happyride" happyride
                         "systembolaget" systembolaget
                         nil))
(defn query-params [watcher-in data] (let [watcher (switch watcher-in)
                                           mapper-fn (:mapper-fn watcher)]
                                       (merge (:search-defaults watcher)
                                              (mapper-fn data)
                                              (:extra-fields data))))

(def example-data {:q "mjolk" :extra-fields {:subcategory "Vitt vin"}})