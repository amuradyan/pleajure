(ns pleajure.core-test
  (:require [clojure.test :refer [is testing]]
            [pleajure.core :refer [consider-entry parse-config parse-entry]]))

(testing "That in pleajure"
  (testing "the name of an entry can only be an atom"
    (is (=
         (parse-entry '(:name :surname))
         [:entry {:name :surname}]))
    (is (=
         (parse-entry '(:ogre {:name "Lactazar" :age 15}))
         [:entry {:ogre {:name "Lactazar" :age 15}}]))
    (is (=
         (parse-entry '(:ogre {:name "Lactazar" :age 15} :nope))
         [:error :entry-is-not-a-pair]))
    (is (=
         (parse-entry '({:name "Pete"} :whatever))
         [:error :entry-name-is-not-atom]))
    (is (=
         (parse-entry '([:name] :whatever))
         [:error :entry-name-is-not-atom]))))

(testing "That the pleajure config can either be"
  (testing "an entry"
    (is (=
         (parse-config '(:name "Pete"))
         [:config {:name "Pete"}]))
    (is (=
         (parse-config '(:name :Pete))
         [:config {:name :Pete}])))
  (testing "a list of entries"
    (is false))
  (testing "or invalid"
    (is (=
         (parse-config '(d (a b) c))
         [:error :invalid-config]))))

(testing "That we are able to distinguish entries"
  (is (= (consider-entry '(name surname)) :valid-entry))
  (is (= (consider-entry '(ogre {name "Lactazar" age 15})) :valid-entry))
  (is (= (consider-entry '({name "Pete"} whatever)) [:error :entry-name-is-not-atom]))
  (is (= (consider-entry '(whatever name name)) [:error :entry-is-not-a-pair])))

