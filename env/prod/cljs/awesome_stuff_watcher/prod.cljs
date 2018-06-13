(ns awesome-stuff-watcher.prod
  (:require [awesome-stuff-watcher.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
