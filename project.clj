(defproject clocon "0.1.0-SNAPSHOT"
  :description "A config tool in clojure"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot clocon.core
  :target-path "target/%s"
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.25.0"]]}})
