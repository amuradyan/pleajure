(ns pleajure.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [pleajure.core :refer [interpret
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
          [:first-name :Shrjoum,
           :last-name :Suzumov,
           :age 26,
           :gender :unrevealed,
           :favorite-color "the best color",
           :likes [:cheese :tea :smalltalk],
           :dislikes [[:first-name :Otar,
                       :last-name :Aperov]
                      [:first-name :Lori,
                       :last-name :Dzu]]]])))
  (testing "And gracefully fail on invalid ones"
    (is (=
         (parse-from-file "resources/broken-test.plj")
         [:error :file-read-failed "Syntax error reading source at (14:1)."]))))


