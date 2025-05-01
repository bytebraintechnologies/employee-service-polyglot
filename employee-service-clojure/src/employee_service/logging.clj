(ns employee-service.logging
  (:require [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders.core :as appenders]))

(defn configure-logging []
  (timbre/set-config!
   {:level :info
    :appenders {:println {:enabled? true}
                :spit (appenders/spit-appender {:fname "log/employee_service.log"})}
    :timestamp-opts {:pattern "yyyy-MM-dd HH:mm:ss"}})
  (timbre/info "Logging configured"))
