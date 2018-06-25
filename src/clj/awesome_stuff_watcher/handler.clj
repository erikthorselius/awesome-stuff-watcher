(ns awesome-stuff-watcher.handler
  (:require [compojure.core :refer [GET defroutes routes]]
            [compojure.route :refer [not-found resources]]
            [ring.util.response :refer [response]]
            [hiccup.page :refer [include-js include-css html5]]
            [awesome-stuff-watcher.middleware :refer [wrap-middleware wrap-api]]
            [config.core :refer [env]]
            [clojure.data.json :as json]
            [taoensso.timbre :as timbre :refer [info]]
            [awesome-stuff-watcher.watcher :as watcher]))

(def mount-target
  [:div#app
   [:h3 "ClojureScript has not been compiled!"]
   [:p "please run "
    [:b "lein figwheel"]
    " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name    "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))


(def api-routes
  (routes
    (GET "/preview" [q] (response {:data (take 5 (watcher/dispatch q))}))))

(defroutes frontend-routes
           (GET "/" [] (loading-page))
           (GET "/about" [] (loading-page))
           (GET "/add-watcher" [] (loading-page))
           (resources "/")
           (not-found "Not Found"))
(def app (routes (wrap-api #'api-routes) (wrap-middleware #'frontend-routes)))
