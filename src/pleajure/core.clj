(ns pleajure.core
  (:gen-class))

(defn -main
  [& _]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
