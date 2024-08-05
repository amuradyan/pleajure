(ns pleajure.core
  (:gen-class)
  (:require [clojure.inspector :refer [atom?]]))


(defn parse-entry
  [entry]
  (let [[name value] entry]
    (cond
      (not (= (count entry) 2)) [:error :not-an-entry]
      (atom? name) [:entry {name value}]
      :else [:error :entry-name-not-atom])))

(defn entry?
  [entry]
  (= (first (parse-entry entry)) :entry))

(defn parse-config
  [config]
  (cond
    (entry? config) [:config (second (parse-entry config))]
    :else [:error :invalid-config]))

(defn -main
  [& _]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
