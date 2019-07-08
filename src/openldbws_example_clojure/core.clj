(ns openldbws-example-clojure.core
  (:require [taoensso.timbre :as timbre
              :refer [log trace debug info warn error
                      fatal report logf tracef debugf infof
                      warnf errorf fatalf reportf spy
                      get-env]])
  (:import (com.thalesgroup.rtti._2013_11_28.token.types AccessToken)
           (com.thalesgroup.rtti._2017_10_01.ldb GetBoardRequestParams)
           (com.thalesgroup.rtti._2017_10_01.ldb LDBServiceSoap)
           (com.thalesgroup.rtti._2017_10_01.ldb Ldb)
           (com.thalesgroup.rtti._2017_10_01.ldb StationBoardResponseType)
           (com.thalesgroup.rtti._2017_10_01.ldb.types ServiceItem)

           (org.apache.cxf.endpoint Client))
  (:gen-class))

(defn -main
  "Main function for this OpenLDBWS example program."
  [& args]

  (def token (System/getenv "LDBWS_TOKEN"))
  (def crs (System/getenv "LDBWS_CRS"))

  ;; set access token to environmental variable
  ;; (TODO: handle invalid/null environment variables)
  (def accessToken
    (doto (AccessToken.)
      (.setTokenValue token)))

  ;; create instance of LDB, and get v12 service
  (def soap (Ldb.))
  (def soapService (.getLDBServiceSoap12 soap))

  ;; set parameters for board request
  (def params
    (doto (GetBoardRequestParams.)
      (.setCrs crs)))

  ;; select departure board service,
  ;; pass parameters
  (def departureBoard
    (.getDepartureBoard soapService
                        params
                        accessToken))

  ;; print informational banner for trains at 'X'
  (infof "Trains at: %s"
         (->
          departureBoard
          (.getGetStationBoardResult ,,,)
          (.getLocationName ,,,)
          ))
  (info "===============================================================================")

  ;; create list of services from 'X'
  (def services
    (->
     departureBoard
     (.getGetStationBoardResult ,,,)
     (.getTrainServices ,,,)
     (.getService ,,,)))

  ;; iterate over list of services
  (doseq [si services]
    (infof "%s to %s from %s - %s"
           (.getStd si)

           (->
           si
           (.getDestination ,,,)
           (.getLocation ,,,)
           (.get ,,, 0)
           (.getLocationName ,,,))

          (->
           si
           (.getOrigin ,,,)
           (.getLocation ,,,)
           (.get ,,, 0)
           (.getLocationName ,,,))

          (.getEtd si))))
