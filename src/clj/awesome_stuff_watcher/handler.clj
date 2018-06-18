(ns awesome-stuff-watcher.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [awesome-stuff-watcher.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [clojure.data.json :as json]
            [taoensso.timbre :as timbre :refer [info]]))

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


(defroutes routes
           (GET "/" [] (loading-page))
           (GET "/about" [] (loading-page))
           (GET "/add-watcher" [] (loading-page))
           (GET "/preview" [watcher q] (do (info (str watcher " " q))
                                           {:status  200
                                            :headers {"Content-Type" "application/json; charset=utf-8"}
                                            :body    (json/write-str {:data "preview data"})}))

           (resources "/")
           (not-found "Not Found"))

(def app (wrap-middleware #'routes))
