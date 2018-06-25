(ns awesome-stuff-watcher.watcher
  (:require [clojure.string :as str]
            [awesome-stuff-watcher.blocket :as blocket]
            [awesome-stuff-watcher.systembolaget :as systembolaget]))
(defn- pick-watcher [url] (cond
                            (str/starts-with? url "https://www.blocket.se") :blocket
                            (str/starts-with? url "https://www.systembolaget.se") :systembolaget
                            (str/starts-with? url "https://www.happyride.se") :happyride
                            :else :watcher-missing))
(defmulti dispatch pick-watcher)
(defmethod dispatch :blocket [url] (blocket/dispatch url))
(defmethod dispatch :systembolaget [url] (systembolaget/dispatch url))
