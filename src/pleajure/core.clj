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
    (not (atom? (first entry))) [:error :entry-name-is-not-atom]
    (not (= (count entry) 2)) [:error :entry-is-not-a-pair]
    :else [:valid-entry entry]))

(defn entry? [subject]
  (= [:valid-entry subject] (consider-entry subject)))

(defn interpret
  [form]
  (cond
    (symbol? form) [:keyword (keyword form)]
    (string? form) [:string form]
    (number? form) [:number form]
    (entry? form) (let [[name-interpretation interpreted-name] (interpret (first form))
                        [value-interpretation interreted-value] (interpret (second form))]
                    (case [name-interpretation value-interpretation]
                      [[:error _] _]  [:error :unknown-form form]
                      [_ [:error _]]  [:error :unknown-form form]
                      [:entry {interpreted-name interreted-value}]))
    (list? form) [:list (map (comp second interpret) form)]
    :else [:error :unknown-form form]))

(defn parse-config
  [config]
  (let [[status value] (interpret config)]
    (case status
      :entry [:config value]
      [:error :invalid-config])))

(defn -main
  [& _]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
