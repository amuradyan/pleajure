(defproject pleajure "0.1.0-SNAPSHOT"
  :description "A config tool in clojure"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.11.1"] [eftest "0.6.0"]]
  :main ^:skip-aot pleajure.core
  :target-path "target/%s"
  :profiles {:dev {:plugins [[lein-eftest "0.6.0"]
                             [com.jakemccrary/lein-test-refresh "0.25.0"]]}})

