(ns pleajure.core-types-test
  (:require [clojure.test :refer [deftest are testing]]
            [pleajure.core :refer [get-at]]))


(deftest get-in-types []
  (testing "And predicatbly fail on invalid inputs for the lookup method"
    (are [actual expected] (= actual expected)
      (get-at 123                  [:a])               :invalid-arguments
      (get-at :not-a-list          [:a])               :invalid-arguments
      (get-at [:config :whatever]  {:not "a list"})    :invalid-arguments
      (get-at [:config :whatever]  'not-a-list)        :invalid-arguments
      (get-at [:config :whatever]  "not a keyword")    :invalid-arguments)))
