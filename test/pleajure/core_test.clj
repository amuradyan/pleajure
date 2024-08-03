(ns pleajure.core-test
  (:require [clojure.test :refer [testing is]]))

;; The first element of an entry, henceforth *the name*,  can only be an atom.
;; The second element of an entry, henceforth *the value*, can be an atom or a list, e.g.

(testing "Pleajure"
  (testing "that the first element of an entry can only be an atom"
    (is (= 5 6))))
