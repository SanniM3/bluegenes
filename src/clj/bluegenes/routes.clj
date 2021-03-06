(ns bluegenes.routes
  (:require [compojure.core :refer [GET defroutes context]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response]]
            [bluegenes.ws.auth :as auth]
            [bluegenes.ws.ids :as ids]
            [bluegenes.ws.rss :as rss]
            [bluegenes.ws.lookup :as lookup]
            [bluegenes.index :as index]
            [config.core :refer [env]]))

; Define the top level URL routes for the server
(defroutes routes
  ;;serve compiled files, i.e. js, css, from the resources folder
  (resources "/")

  (GET "/version" [] (response {:version "0.1.0"}))

  ; Anything within this route is the API web service:
  (context "/api" []
    (context "/auth" [] auth/routes)
    (context "/ids" [] ids/routes)
    (context "/rss" [] rss/routes))

  (context (str "/" (:bluegenes-default-namespace env)) []
    (GET ["/:lookup" :lookup #"[^:/.]+:[^:/.]+"] [lookup] (lookup/ws lookup)))

  ;; One of BlueGenes' web service could have added some data we want passed on
  ;; to the frontend to session.init, in which case we make sure to pass it on
  ;; and remove it (as it gets "consumed") from the session.
  (GET "*" {{:keys [init] :as session} :session}
    (-> (response (index/index init))
        (assoc :session (dissoc session :init)))))
