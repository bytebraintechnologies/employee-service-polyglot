(defproject employee-service "0.1.0-SNAPSHOT"
  :description "Employee Service Clojure Implementation"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [ring/ring-defaults "0.3.4"]
                 [ring/ring-json "0.5.1"]
                 [ring-cors "0.1.13"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [metosin/ring-http-response "0.9.3"]
                 [com.taoensso/timbre "6.1.0"]
                 [org.clojure/data.json "2.4.0"]]
  :plugins [[lein-ring "0.12.6"]]
  :ring {:handler employee-service.minimal-handler/app
         :port 8087}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.4.0"]]}})
