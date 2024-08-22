(ns pleajure.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [pleajure.core :refer [consider-entry interpret interpret-list
                                   parse-config]]))

(deftest interpreter []
  (testing "That in pleajure"
    (testing "the name of an entry can only be an atom"
      (is (=
           (interpret '((name surname)))
           [:map {:name :surname}]))
      (is (=
           (interpret '(number 2))
           [:list [:number 2]]))
      (is (=
           (interpret '(ogre (one 2 (three)) erog))
           [:list [:ogre [:one 2 [:three]] :erog]]))
      (is (=
           (interpret '(ogre (name "Lactazar")))
           [:list [:ogre [:name "Lactazar"]]]))
      (is (=
           (interpret '(ogre ((name "Lactazar") (age 15))))
           [:list [:ogre {:name "Lactazar" :age 15}]]))
      (is (=
           (interpret '((ogre ((name "Lactazar") (age 15)))))
           [:map {:ogre {:name "Lactazar" :age 15}}]))
      (is (=
           (consider-entry 'a)
           [:error :entry-is-not-a-pair])))))

(deftest config-parser []
  (testing "That the pleajure config can either be"
    (testing "an entry"
      (is (=
           (parse-config '((name "Pete")))
           [:config {:name "Pete"}]))
      (is (=
           (parse-config '((name Pete)))
           [:config {:name :Pete}])))
    (testing "a list of entries"
      (is false))
    (testing "or invalid"
      (is (=
           (parse-config '(d (a b) c))
           [:error :invalid-config])))))

(deftest entry-filter []
  (testing "That we are able to distinguish entries"
    (is (=
         (consider-entry 'name)
         [:error :entry-is-not-a-pair]))
    (is (=
         (consider-entry '(ogre ((name "Lactazar") (age 15)) :nope))
         [:error :entry-is-not-a-pair]))
    (is (=
         (consider-entry '((name) whatever))
         [:error :entry-name-is-not-atom]))
    (is (=
         (consider-entry '(name surname))
         [:valid-entry '(name surname)]))
    (is (=
         (consider-entry '(ogre {name "Lactazar" age 15}))
         [:valid-entry '(ogre {name "Lactazar" age 15})]))
    (is (=
         (consider-entry '((name "Pete") whatever))
         [:error :entry-name-is-not-atom]))
    (is (=
         (consider-entry '(whatever name name))
         [:error :entry-is-not-a-pair]))))

(deftest list-interpreter []
  (testing "That pleajure can express"
    (testing "plain lists"
      (is (=
           (interpret-list '(a b c))
           [:list [:a :b :c]]))
      (is (=
           (interpret-list '())
           [:list []])))
    (testing "nested lists"
      (is (=
           (interpret-list '((a b) c))
           [:list [[:a :b] :c]])))
    (testing "plain maps"
      (is (=
           (interpret-list '((a b)))
           [:map {:a :b}]))
      (is (=
           (interpret-list '((a b) (c d)))
           [:map {:a :b :c :d}])))
    (testing "and it responds with the appropriate type" ; This checks the implentaion, not the use case
      (is (=
           (interpret-list '() [:f :G] {:f :G} true)
           [:map {:f :G}]))
      (is (=
           (interpret-list '() [:f :G] {} false)
           [:list [:f :G]])))))

(deftest keywords []
  (testing  "That pleajure interprets symbols that could be"
    (testing "keywords as keywords"
      (is (=
           (interpret 'name)
           [:keyword :name])))
    (testing "numbers as numbers"
      (is (=
           (interpret '2)
           [:number 2])))
    (testing "strings as strings"
      (is (=
           (interpret '"name")
           [:string "name"])))
    (testing "lists as lists"
      (is (=
           (interpret '(name))
           [:list [:name]])))))
