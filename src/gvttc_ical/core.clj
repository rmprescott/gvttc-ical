(ns gvttc-ical.core
	(:require 
    [clojure.tools.cli :refer [parse-opts]]
		[gvttc-ical.data-2015 :as data] ;; get dynamic loading to work
  )
  (:gen-class))

(defn calendar [p leagues]
  (str "TODO: calendar for player: " p))

(defn all-calendars 
  "create a lazy sequence of the calendar for each player"
  [players leagues]
  (for [p players]
    { :player p
      :calendar (calendar p leagues)}))

(defn emit-all-calendars! [dir players leagues]
  (let [calendars (all-calendars players leagues)]
    (doseq [{:keys [player calendar]} calendars]
      (let [file (format "%s/%s.ics" dir player)]
        (println dir ":" player calendar)))))

;; TODO: integrate better CLI options using: https://github.com/clojure/tools.cli
(defn -main
  ""
  [& args]
  (let [year "2015" ;; TODO: parse from arguments
        data (str  "data-" year) 
        dir "ical" ] 
  (println data)
  ;; (require '(gvttc-ical data-2015)) ;; TODO: get dynamic loading to work
  (emit-all-calendars! dir data/players data/leagues)
  ))