(ns awesome-stuff-watcher.watcher
  (:require [clojure.string :as str]
            [clj-http.client :as client]
            [hickory.core :as h]
            [hickory.select :as s]))
(defn- blocket-image [element])


(defn- pick-watcher [url] (cond
                            (str/starts-with? url "https://www.blocket.se") :blocket
                            (str/starts-with? url "https:/www.systembolaget,se") :systembolaget
                            (str/starts-with? url "https://www.happyride.se") :happyride
                            :else :watcher-missing))
(defmulti dispatch pick-watcher)
(defmethod dispatch :blocket [url] (let [response (client/get url)]
                                     (->>(:body response)
                                         (h/parse)
                                         (h/as-hickory)
                                         (s/select
                                           (s/child (s/id "item_list")
                                                    (s/tag :article))))))