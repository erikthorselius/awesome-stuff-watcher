(ns awesome-stuff-watcher.middleware
  (:require [ring.middleware.defaults :refer [site-defaults wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]))
(defn wrap-middleware [handler]
  (wrap-defaults handler site-defaults))
(defn wrap-api [handler]
  (-> handler
      wrap-json-response
      (wrap-defaults api-defaults)))
