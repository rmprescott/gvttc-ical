(ns gvttc-ical.core-test
  (:require [clojure.test :refer :all]
            [gvttc-ical.core :refer :all]
            [gvttc-ical.data-2015 :as data]
            ))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))
    (is (= 1 1))
    (is (= 1 0))
    (is (= 0 0))
  ))

(deftest data-is-available
  (testing "data is available")
    (is (resolve 'data/players))
    (is (resolve 'data/monday-league))
  )


