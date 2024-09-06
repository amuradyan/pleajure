(ns pleajure.core-inputs-test
  (:require [clojure.test :refer [deftest are testing]]
            [pleajure.core :refer [get-at list-lookup is-config? is-path?]]))

(deftest config-filter
  (testing "that we know a config when we see one"
    (are [actual expected] (= actual expected)
      (is-config? [:config [:a :b]]) true
      (is-config? []) false
      (is-config? [:a :b]) false
      (is-config? "[:a :b :c]") false
      (is-config? [:config :not]) false
      (is-config? [:config [:a :b] :c]) false)))

(deftest path-filter
  (testing "that we know a path when we see one"
    (are [actual expected] (= actual expected)
      (is-path? :c) true
      (is-path? []) true
      (is-path? [:a :b]) true
      (is-path? "[:a :b]") false
      (is-path? {:a :B}) false
      (is-path? [:a [:b] :c]) false)))

(deftest get-in-inputs []
  (testing "And predicatbly fail on invalid inputs for the lookup method"
    (are [actual expected] (= actual expected)
      (get-at 123              [:a])              :invalid-config
      (get-at :not-a-list      [:a])              :invalid-config
      (get-at [:config :not]   {:not "a list"})   :invalid-config
      (get-at [:config [:not]] {:not "a list"})   :invalid-path
      (get-at [:config []]     [:bad [:path]])    :invalid-path
      (get-at [:config [:not]] "not a keyword")   :invalid-path)))

(deftest list-lookup-inputs []
  (testing "Predicatbly fail on invalid inputs for the list lookup method"
    (are [actual expected] (= actual expected)
      (list-lookup 123                  [:a])               :invalid-data
      (list-lookup :not-a-list          [:a])               :invalid-data
      (list-lookup [:config :whatever]  {:not "a list"})    :invalid-path
      (list-lookup [:config :whatever]  'not-a-list)        :invalid-path
      (list-lookup [:config :whatever]  ["not a keyword"])  :invalid-path)))
