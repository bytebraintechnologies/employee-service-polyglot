(ns employee-service.logging
  (:require [taoensso.timbre :as timbre]))

;; Simplest possible logging setup to avoid any potential issues
(defn configure-logging []
  (timbre/set-config!
   {:level :info
    :ns-whitelist []
    :ns-blacklist []
    :middleware []
    :timestamp-opts {:pattern "yyyy-MM-dd HH:mm:ss"}
    :output-fn timbre/default-output-fn})
  (timbre/info "Logging initialized"))