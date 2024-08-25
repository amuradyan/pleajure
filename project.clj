(defproject am.dekanat/pleajure "0.0.3"
  :description "A LISP-like config notation"
  :url "https://amuradyan.github.io/pleajure/"
  :license {:name         "Zero-Clause BSD"
            :url          "https://opensource.org/license/0bsd"
            :distribution :repo}
  :scm {:name "git" :url "https://github.com/amuradyan/pleajure"}
  :pom-addition ([:developers
                  [:developer
                   [:id "amuradyan"]
                   [:name "Andranik Muradyan"]
                   [:url "https://github.com/amuradyan"]
                   [:roles
                    [:role "developer"]
                    [:role "maintainer"]]]])
  :classifiers [["sources" {:source-paths      ^:replace []
                            :resource-paths    ^:replace ["javadoc"]}]
                ["javadoc" {:source-paths      ^:replace []
                            :resource-paths    ^:replace ["javadoc"]}]]
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :target-path "target/%s"
  :profiles {:dev {:plugins [[lein-eftest "0.6.0"]
                             [com.jakemccrary/lein-test-refresh "0.25.0"]]}}
  :deploy-repositories [["releases" {:url "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                                     :creds :gpg}
                         "snapshots" {:url "https://oss.sonatype.org/content/repositories/snapshots/"
                                      :creds :gpg}]])

