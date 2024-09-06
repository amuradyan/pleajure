(ns pleajure.core
  (:gen-class))

(defn interpret
  [form]
  (letfn [(interpret-symbol [form] [:keyword (keyword form)])
          (interpret-string [form] [:string form])
          (interpret-number [form] [:number form])
          (interpret-list   [form] [:list (map (comp second interpret) form)])]
    (cond
      (symbol? form) (interpret-symbol form)
      (string? form) (interpret-string form)
      (number? form) (interpret-number form)
      (list? form) (interpret-list form)
      :else [:error :unknown-form form])))

(defn parse-config
  [config]
  (let [[status value] (interpret config)]
    (case status
      :list [:config value]
      [:error :invalid-config])))

(defn parse-from-file
  [file]
  (try
    (parse-config (load-string (str "'" (slurp file))))
    (catch Exception e
      [:error :file-read-failed (ex-message e)])))

(defn is-path? [data]
  (or
   (keyword? data)
   (and
    (vector? data)
    (every? keyword? data))))

(defn list-lookup
  [data path]
  (cond
    (not (vector? data))  :invalid-data    ;; sanity check
    (not (is-path? path)) :invalid-path    ;; sanity check
    (empty? path) data
    (empty? data) :invalid-path
    :else (let [[first-value & rv] data
                [next-value & _] rv
                [first-key & rk] path
                rest-of-the-values (into [] rv)
                rest-of-the-keys (into [] rk)]
            (cond
              (= first-key first-value)
              (if (empty? rest-of-the-keys)
                (if (empty? rest-of-the-values)
                  :invalid-path
                  (first rest-of-the-values))
                (list-lookup next-value rest-of-the-keys))
              :else (list-lookup rest-of-the-values path)))))

(defn is-config?
  [data]
  (and
   (vector? data)
   (= (count data) 2)
   (= (first data) :config)
   (vector? (second data))))

(defn get-at
  [config path]
  (cond
    (not (is-config? config)) :invalid-config   ;; sanity check
    (not (is-path? path)) :invalid-path         ;; sanity check
    (empty? path) config
    (empty? config) :invalid-path
    :else (list-lookup (second config) path)))
