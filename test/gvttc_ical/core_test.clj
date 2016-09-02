(ns gvttc-ical.core-test
  (:require [clojure.test :refer :all]
            [gvttc-ical.core :refer :all]
            [gvttc-ical.data-2015 :as data]
            ))

;; move to core


(deftest data-is-available
 (testing "data is available"
    (is (resolve 'data/players))
   (is (resolve 'data/monday-league))
  ))

(deftest test-plays-in?
  (testing "plays-in? predicate"
    (is (plays-in? :rprescott data/monday-league))
    (is (plays-in? :rprescott data/tuesday-league))
    (is (not (plays-in? :rprescott data/wednesday-league)))
    (is (= [:monday :tuesday] (map :type (filter #(plays-in? :rprescott %) data/leagues))))
))

;;(deftest test-monday-events
;;  (testing 
;;    (is 
   
(deftest monday-only-player
  (testing "a player from Monday only"
	  (let []
      (is :todo))))


;; @TODO: remove this
(run-tests)
