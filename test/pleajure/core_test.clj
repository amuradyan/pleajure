(ns pleajure.core-test
  (:require [clojure.test :refer [is testing]]
            [pleajure.core :refer [parse-entry]]))

(testing "That in pleajure"
  (testing "the name of an entry can only be an atom"
    (is (= (parse-entry '(:name :surname)) [:entry :name :surname]))
    (is (=
         (parse-entry '(:ogre {:name "Lactazar" :age 15}))
         [:entry :ogre {:name "Lactazar" :age 15}]))
    (is (= (parse-entry '({:name "Pete"} :whatever)) [:error :entry-name-not-atom]))
    (is (= (parse-entry '([:name] :whatever)) [:error :entry-name-not-atom]))))
