(ns pleajure.core
  (:gen-class))

(declare interpret-list)

(defn atom? [subject]
  (or
   (instance? clojure.lang.Symbol subject)
   (instance? clojure.lang.Keyword subject)
   (instance? clojure.lang.Atom subject)))

(defn consider-entry
  [entry]
  (cond
    (not (list? entry)) [:error :entry-is-not-a-pair]
    (not (atom? (first entry))) [:error :entry-name-is-not-atom]
    (not (= (count entry) 2)) [:error :entry-is-not-a-pair]
    :else [:valid-entry entry]))

(defn interpret
  [form]
  (cond
    (symbol? form) [:keyword (keyword form)]
    (string? form) [:string form]
    (number? form) [:number form]
    (list? form) (interpret-list form)
    :else [:error :unknown-form form]))

(defn interpret-list
  ([form]
   (interpret-list form [] {} true))

  ([form list-instance map-instance probable-map?]
   (cond
     (empty? form) (let [first-round? (empty? list-instance)]
                     (cond
                       (and probable-map? first-round?) [:list list-instance]
                       probable-map? [:map map-instance]
                       :else [:list list-instance]))
     :else (let
            [[current & rest] form
             [errors? _] (consider-entry current)
             still-probable-map? (and probable-map? (not (= errors? :error)))]
             (if still-probable-map?
               (let [[_ interpreted-name] (interpret (first current))
                     [_ interreted-value] (interpret (second current)) ;; What about the checks here?
                     updated-list-instance (conj list-instance [interpreted-name interreted-value])
                     updated-map-instance (assoc map-instance interpreted-name interreted-value)]
                 (interpret-list rest updated-list-instance updated-map-instance still-probable-map?))
               (let [updated-list-instance (conj list-instance ((comp second interpret) current))]
                 (interpret-list rest updated-list-instance map-instance still-probable-map?)))))))

(defn parse-config
  [config]
  (let [[status value] (interpret config)]
    (case status
      :map [:config value]
      [:error :invalid-config])))

(defn parse-from-file
  [file]
  (try
    (parse-config (load-string (str "'" (slurp file))))
    (catch Exception e
      [:error :file-read-failed (ex-message e)])))

(defn -main
  [& _]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
