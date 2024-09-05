(ns pleajure.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [pleajure.core :refer [interpret get-at list-lookup
                                   parse-config parse-from-file]]))

(deftest parsing-configs []
  (testing "That a config is effectively a list"
    (is (=
         (parse-config '(name Pete age 15))
         [:config [:name :Pete :age 15]])))
  (testing "That what's not a list, is not a config"
    (is (=
         (parse-config 'not-a-config)
         [:error :invalid-config]))))

(deftest interpreting-forms []
  (testing  "That pleajure interprets symbols that"
    (testing "could be keywords as keywords"
      (is (=
           (interpret 'name)
           [:keyword :name])))
    (testing "could be numbers as numbers"
      (is (=
           (interpret '2)
           [:number 2])))
    (testing "could be strings as strings"
      (is (=
           (interpret '"name")
           [:string "name"])))
    (testing "could be lists as lists"
      (is (=
           (interpret '(name))
           [:list [:name]]))
      (is (=
           (interpret '(ogre (one 2 (three)) erog))
           [:list [:ogre [:one 2 [:three]] :erog]]))
      (is (=
           (interpret '(ogre (name "Lactazar") (age 15)))
           [:list [:ogre [:name "Lactazar"] [:age 15]]])))
    (testing "could be unknown forms as errors"
      (is (=
           (interpret ':name)
           [:error :unknown-form :name]))
      (is (=
           (interpret '{:name "name"})
           [:error :unknown-form {:name "name"}])))))

(deftest reading-config-from-file []
  (testing "That pleajure can parse a config file"
    (is (=
         (parse-from-file "resources/test.plj")
         [:config
          [:first-name :Shrjoum
           :last-name :Suzumov
           :age 26
           :gender :unrevealed
           :favorite-color "the best color"
           :likes [:cheese :tea :smalltalk]
           :dislikes [[:first-name :Otar
                       :last-name :Aperov]
                      [:first-name :Lori
                       :last-name :Dzu]]]])))
  (testing "And gracefully fail on invalid ones"
    (is (=
         (parse-from-file "resources/broken-test.plj")
         [:error :file-read-failed "Syntax error reading source at (14:1)."]))))

;; ^:test-refresh/focus
(deftest fetching-values []
  (let [data [:valid [:nested [:path 26]] :valid-path "value-2"]
        config [:config data]]
    (testing "That pleajure can fetch a values at a given path"
      (is (=
           (get-at config [])
           config))
      (is (=
           (list-lookup data [:valid :nested :path])
           26))
      (is (=
           (list-lookup data [:valid-path])
           "value-2")))
    (testing "And gracefully fail on invalid paths"
      (is (=
           (get-at [:config [:valid :path]] [:broken :path])
           :invalid-path))
      (is (=
           (list-lookup [] [:broken :path])
           :invalid-path)))
    (testing "And gracefully fail on invalid configs"
      (is (=
           (get-at [:not :a :valid-config] [:a])
           :not-a-config))
      (is (=
           (get-at [:config] [:a])
           :not-a-config))
      (is (=
           (get-at [:config :whatever] [:a])
           :not-a-config)))))
