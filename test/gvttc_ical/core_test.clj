(ns gvttc-ical.core-test
  (:require [clojure.test :refer :all]
            [gvttc-ical.core :refer :all]
            ;;[gvttc-ical.data-2015 :as data]
            [clojure.edn :as edn]
            [taoensso.timbre :as log]
            ))

;; move to core

(defn load-test-data "Read test data from a file."
  [test-fn]
  (log/info "Loading test data")
  (def test-players (edn/read-string (slurp "test/gvttc_ical/players.edn")))
  (def test-leagues (edn/read-string (slurp "test/gvttc_ical/leagues.edn")))
  (def monday-league (first test-leagues))
  (def tuesday-league (second test-leagues))
  
  (test-fn))

(deftest test-data-is-sane? 
  (testing "sanity checks of test data"
    (is (> (count test-players) 10))
    (is (>= (count test-leagues) 2))
    (is (= (monday-league :type) :monday ))
    (is (= (tuesday-league :type) :tuesday))
  ))



(deftest test-plays-in?
  (testing "plays-in? predicate with edn test data"
    ;; :cronaldo plays on Monday but not Tuesday
    (is (plays-in? :cronaldo monday-league))
    (is (not (plays-in? :cronaldo tuesday-league)))
    (is (= [:monday] (map :type (filter #(plays-in? :cronaldo %) test-leagues))))
    ;; :mneuer plays both Monday and Tuesday
    (is (= [:monday :tuesday] (map :type (filter #(plays-in? :mneuer %) test-leagues))))
    ;; :gbale plays only Monday
    (is (= [:monday] (map :type (filter #(plays-in? :gbale %) test-leagues))))
    ;; :ecantona doesn't play in any league
    (is (= [] (map :type (filter #(plays-in? :ecantona %) test-leagues))))
))

(deftest monday-only-player
  (testing "a player from Monday only"
	  (let []
      (is :todo))))

(deftest todo-name-this-test 
  (testing "todo: what is this testing?"
    (println (event :cronaldo monday-league nil))
))

(use-fixtures :once load-test-data)

;; @TODO: remove this
(run-tests)
