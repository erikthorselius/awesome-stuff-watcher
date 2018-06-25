(ns awesome-stuff-watcher.middleware
  (:require [ring.middleware.defaults :refer [site-defaults wrap-defaults api-defaults]]
            [ring.middleware.json :refer [wrap-json-response]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.logger :as logger]))

(defn wrap-middleware [handler]
  (-> handler
      (wrap-defaults site-defaults)
      wrap-exceptions
      wrap-reload))

(defn wrap-api [handler]
  (-> handler
      wrap-json-response
      (wrap-defaults api-defaults)
      wrap-reload
      logger/wrap-with-logger))
