(ns pleajure.core-types-test
  (:require [clojure.test :refer [deftest are testing]]
            [pleajure.core :refer [get-at list-lookup]]))


(deftest get-in-types []
  (testing "And predicatbly fail on invalid inputs for the lookup method"
    (are [actual expected] (= actual expected)
      (get-at 123                  [:a])               :invalid-arguments
      (get-at :not-a-list          [:a])               :invalid-arguments
      (get-at [:config :whatever]  {:not "a list"})    :invalid-arguments
      (get-at [:config :whatever]  'not-a-list)        :invalid-arguments
      (get-at [:config :whatever]  "not a keyword")    :invalid-arguments)))

(deftest list-lookup-types []
  (testing "Predicatbly fail on invalid inputs for the list lookup method"
    (are [actual expected] (= actual expected)
      (list-lookup 123                  [:a])               :data-not-a-vector
      (list-lookup :not-a-list          [:a])               :data-not-a-vector
      (list-lookup [:config :whatever]  {:not "a list"})    :path-not-a-vector
      (list-lookup [:config :whatever]  'not-a-list)        :path-not-a-vector
      (list-lookup [:config :whatever]  ["not a keyword"])  :key-not-an-atom)))
