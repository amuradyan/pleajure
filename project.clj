(defproject am.dekanat/pleajure "0.0.4"
  :description "A LISP-like config notation"
  :url "https://amuradyan.github.io/pleajure/"
  :license {:name         "Zero-Clause BSD"
            :url          "https://opensource.org/license/0bsd"
            :distribution :repo}
  :scm {:name "git" :url "https://github.com/amuradyan/pleajure"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.25.0"]]}}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]])

