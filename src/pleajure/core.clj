(ns pleajure.core
  (:gen-class))

(defn interpret
  [form]
  (letfn [(interpret-symbol [form] [:keyword (keyword form)])
          (interpret-string [form] [:string form])
          (interpret-number [form] [:number form])
          (interpret-list   [form] [:list (map (comp second interpret) form)])]
    (cond
      (symbol? form)  (interpret-symbol form)
      (string? form)  (interpret-string form)
      (number? form)  (interpret-number form)
      (list?   form)  (interpret-list form)
      :else           [:error :unknown-form form])))  ;; sanity check

(defn parse-config
  [config]
  (cond
    (not (list? config)) [:error :invalid-raw-config]  ;; sanity check
    :else (let [[status value] (interpret config)]
            (case status
              :list [:config value]
              [:error :invalid-raw-config]))))

(defn parse-from-file
  [file]
  (cond
    (not (string? file)) :invalid-file-path  ;; sanity check
    :else (try
            (parse-config (load-string (str "'" (slurp file))))
            (catch Exception e
              [:error :file-read-failed (ex-message e)]))))

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
    :else (if (= (first data) (first path))
            (if (empty? (rest path))
              (if (empty? (rest data))
                :invalid-path
                (first (rest data)))
              (list-lookup (first (into [] (rest data))) (into [] (rest path))))
            (list-lookup (into [] (rest data)) path))))

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
    :else (list-lookup (second config) path)))
