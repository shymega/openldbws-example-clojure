(defproject openldbws_example_clojure "1.0.0"
  :description "Example code to use the OpenLDBWS in Clojure with Apache CXF."
  :url ""
  :license {:name "Apache License 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [org.apache.cxf/cxf-rt-frontend-jaxws "3.3.0"]
                 [org.apache.cxf/cxf-rt-transports-http "3.3.0"]]
  :main ^:skip-aot openldbws-example-clojure.core
  :target-path "target/%s"
  :java-source-paths ["target/generated/java"]
  :plugins [[lein-wsimport "1.0.0"]]
  :wsimport {:wsdl-list ["https://lite.realtime.nationalrail.co.uk/OpenLDBWS/wsdl.aspx?ver=2017-10-01"]
             :quiet-output true
            :extra-options ["-extension" "-XadditionalHeaders"]}
  :profiles {:uberjar {:aot :all}})
