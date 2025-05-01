(ns employee-service.standalone-server
  (:require [clojure.string :as str]
            [clojure.data.json :as json])
  (:import [java.net ServerSocket Socket InetSocketAddress]
           [java.io BufferedReader InputStreamReader PrintWriter]
           [java.util.concurrent Executors]))

(defn handle-request [input-reader output-writer]
  (try
    (let [request-line (.readLine input-reader)
          method-path (str/split request-line #" ")
          path (if (> (count method-path) 1) (nth method-path 1) "/")]
      
      ;; Read headers
      (loop [line (.readLine input-reader)]
        (when (not (or (nil? line) (= line "")))
          (recur (.readLine input-reader))))
      
      ;; Generate response based on path
      (cond
        (= path "/health") 
          (do
            (.println output-writer "HTTP/1.1 200 OK")
            (.println output-writer "Content-Type: application/json")
            (.println output-writer "")
            (.println output-writer (json/write-str {:status "UP"})))
        
        (= path "/") 
          (do
            (.println output-writer "HTTP/1.1 200 OK")
            (.println output-writer "Content-Type: application/json")
            (.println output-writer "")
            (.println output-writer (json/write-str {:message "Employee Service API"})))
        
        :else 
          (do
            (.println output-writer "HTTP/1.1 404 Not Found")
            (.println output-writer "Content-Type: application/json")
            (.println output-writer "")
            (.println output-writer (json/write-str {:error "Not Found"})))))
    (catch Exception e
      (println "Error handling request:" (.getMessage e))
      (.println output-writer "HTTP/1.1 500 Internal Server Error")
      (.println output-writer "Content-Type: application/json")
      (.println output-writer "")
      (.println output-writer (json/write-str {:error "Internal Server Error"})))))

(defn handle-client [client-socket]
  (with-open [socket client-socket
              input (BufferedReader. (InputStreamReader. (.getInputStream socket)))
              output (PrintWriter. (.getOutputStream socket) true)]
    (handle-request input output)))

(defn start-server [port]
  (println (str "Starting standalone server on port " port))
  (let [server-socket (ServerSocket. port)
        thread-pool (Executors/newFixedThreadPool 10)]
    (future
      (try
        (while true
          (let [client-socket (.accept server-socket)]
            (.submit thread-pool #(handle-client client-socket))))
        (catch Exception e
          (println "Server error:" (.getMessage e)))
        (finally
          (.shutdown thread-pool)
          (.close server-socket))))
    server-socket))

(defn -main [& args]
  (let [port (Integer/parseInt (or (first args) "8087"))]
    (start-server port)
    (println "Server started. Press Ctrl+C to exit.")
    ;; Keep main thread running
    @(promise)))