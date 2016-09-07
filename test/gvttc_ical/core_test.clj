(ns gvttc-ical.core-test
  (:require [clojure.test :refer :all]
            [gvttc-ical.core :refer :all]
            [gvttc-ical.data-2015 :as data]
            [clojure.edn :as edn]
            [taoensso.timbre :as log]
            ))

;; move to core

(def test-players (atom nil))
(def test-leagues (atom nil))

(defn load-test-data "Read test data from a file."
  [test-fn]
  (log/info "Loading test data")
  (reset! test-players (edn/read-string (slurp "test/gvttc_ical/players.edn")))
  (reset! test-leagues (edn/read-string (slurp "test/gvttc_ical/leagues.edn")))
  
  (test-fn))

(deftest player-test-data-is-available 
  (testing "player test data is loaded from edn"
     (is (> (count @test-players) 0))
  ))

(deftest league-test-data-is-available 
  (testing "league test data is loaded from edn"
     (is (> (count @test-leagues) 0))
  ))

(deftest test-plays-in-edn?
  (testing "plays-in? predicate with edn test data"
    ;; :cronaldo plays on Monday but not Tuesday
    (is (plays-in? :cronaldo (first @test-leagues)))
    (is (not (plays-in? :cronaldo (second @test-leagues))))
    (is (= [:monday] (map :type (filter #(plays-in? :cronaldo %) @test-leagues))))
    ;; :mneuer plays both Monday and Tuesday
    (is (= [:monday :tuesday] (map :type (filter #(plays-in? :mneuer %) @test-leagues))))
    ;; :gbale plays only Monday
    (is (= [:monday] (map :type (filter #(plays-in? :gbale %) @test-leagues))))
    ;; :ecantona doesn't play in any league
    (is (= [] (map :type (filter #(plays-in? :ecantona %) @test-leagues))))
))

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


(deftest monday-only-player
  (testing "a player from Monday only"
	  (let []
      (is :todo))))

(use-fixtures :once load-test-data)

;; @TODO: remove this
(run-tests)
