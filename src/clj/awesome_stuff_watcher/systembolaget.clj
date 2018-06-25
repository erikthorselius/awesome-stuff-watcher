(ns awesome-stuff-watcher.systembolaget
  (:require [clojure.string :as str]
            [clj-http.client :as client]
            [clojure.data.json :as json]))
(defn- item [json] {:img   (str "https:" (get-in json [:Thumbnail :ImageUrl] "https://static.systembolaget.se/content/assets/images/products/thumbnail_noimage.png"))
                    :price (:PriceText json)
                    :url   (str "https://www.systembolaget.se" (:ProductUrl json))
                    :title (:ProductNameBold json)})
(defn- swap-to-api [url] (str/replace url #"https://www.systembolaget.se/sok-dryck/" "https://www.systembolaget.se/api/productsearch/search/sok-dryck/"))
(defn- to-json [response] (-> (:body response)
                              (json/read-str :key-fn keyword)
                              (:ProductSearchResults)))
(defn dispatch [url]
  (let [response (client/get (swap-to-api url))]
    (->> (to-json response)
         (map item))))