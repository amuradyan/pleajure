(ns clocon.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (load-string (str "'" (slurp "resources/test.plj")))))
