(ns pleajure.core
  (:gen-class)
  (:require [clojure.inspector :refer [atom?]]))


(defn parse-entry
  [entry]
  (let [[name value] entry]
    (cond
      (atom? name) (list :entry name value)
      :else [:error :entry-name-not-atom])))

;; (defn -main
;;   [& _]
;;   (println (load-string (str "'" (slurp "resources/test.plj")))))
