(ns bubble-time.web
  (:require [ring.util.response :refer [file-response]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.edn :refer [wrap-edn-params]]
            [compojure.core :refer [defroutes GET PUT POST]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [datomic.api :as d]))

(def uri "datomic:free://localhost:4334/bubble-time")
(def conn (d/connect uri))

(defn index []
  (file-response "public/html/index.html" {:root "resources"}))

(defn generate-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/edn"}
   :body (pr-str data)})

(defn get-classes [db]
  (->> (d/q '[:find ?class
              :where
              [?class :class/id]]
          db)
       (map #(d/touch (d/entity db (first %))))
       vec))

(defn create-class [params]
  {:status 500})

(defn update-class [params]
  (let [id    (:class/id params)
        db    (d/db conn)
        title (:class/title params)
        eid   (ffirst
               (d/q '[:find ?class
                      :in $ ?id
                      :where
                      [?class :class/id ?id]]
                    db id))]
    (generate-response (try
                         {:status (if @(d/transact conn [[:db/add eid :class/title title]])
                                    :ok
                                    :fail)}
                         (catch Exception e {:status {:fail (str "caught exception: " (.getMessage e))}})))))

(defn classes []
  (generate-response (get-classes (d/db conn))))

(defn init []
  (generate-response
     {:classes {:url "/classes" :coll (get-classes (d/db conn))}}))

(defroutes routes
  (GET "/" [] (index))
  (GET "/init" [] (init))
  (POST "/classes" {params :edn-params} (create-class params))
  (PUT "/classes" {params :edn-params} (update-class params))
  (route/files "/" {:root "resources/public"}))

(def app
  (-> routes
      wrap-edn-params))

(defonce server
  (run-jetty #'app {:port 8080 :join? false}))
