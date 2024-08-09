(ns pleajure.core
  (:gen-class))

(defn atom? [subject]
  (or
   (instance? clojure.lang.Symbol subject)
   (instance? clojure.lang.Keyword subject)
   (instance? clojure.lang.Atom subject)))

(defn consider-entry
  [entry]
  (cond
    (not (= (count entry) 2)) [:error :entry-is-not-a-pair]
    (not (atom? (first entry))) [:error :entry-name-is-not-atom]
    :else :valid-entry))

(defn interpret
  [form]
  (cond
    (symbol? form) (keyword form)
    (string? form) form
    (number? form) form
    :else [:error :unknown-form form]))

(defn parse-entry
  [raw-entry]
  (let [[raw-name raw-value] raw-entry
        entry-validity (consider-entry raw-entry)]
    (case entry-validity
      :valid-entry [:entry {(interpret raw-name) (interpret raw-value)}]
      entry-validity)))

(defn entry? [subject]
  (= :valid-entry (consider-entry subject)))

(defn parse-config
  [config]
  (cond
    (entry? config) [:config (second (parse-entry config))]
    :else [:error :invalid-config]))

(defn -main
  [& _]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
