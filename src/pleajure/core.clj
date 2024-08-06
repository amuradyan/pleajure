(ns pleajure.core
  (:gen-class)
  (:require [clojure.inspector :refer [atom?]]))


(defn consider-entry
  [entry]
  (cond
    (not (= (count entry) 2)) [:error :entry-is-not-a-pair]
    (not (atom? (first entry))) [:error :entry-name-is-not-atom]
    :else :valid-entry))

(defn parse-entry
  [raw-entry]
  (let [[name value] raw-entry
        entry-validity (consider-entry raw-entry)]
    (case entry-validity
      :valid-entry [:entry {(keyword name) value}]
      entry-validity)))

(defn entry? [raw-entry]
  (= :valid-entry (consider-entry raw-entry)))

(defn parse-config
  [config]
  (cond
    (entry? config) [:config (second (parse-entry config))]
    :else [:error :invalid-config]))

(defn -main
  [& _]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
