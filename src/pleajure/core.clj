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
