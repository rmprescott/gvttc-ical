(ns gvttc-ical.core
	(:require 
    [clojure.tools.cli :refer [parse-opts]]
    [taoensso.timbre :as log]
		[gvttc-ical.data-2015 :as data] ;; get dynamic loading to work
    [clj-ical.format :as ical]
  )
  (:gen-class))

(defn plays-in? [pid league]
  (some #{pid} (flatten (:teams league))))

(defn all-players [all-leagues]
  (->> (map :teams data/leagues) flatten distinct (filter #(not (nil? %)))))

(defn event [p league night]
  (println night)
  (let
    [start-time (fn [d] (println "d: " (class d)) (. d setTime (:start-time league)))]
    { ;;:start-time (start-time (key night))
      :format :todo
      :vs-team :todo
      :vs-players [:todo :todo :todo :todo]
    }
  ))

(defn events [p league]
  (for [e (:events league)]
    (event p league e)))

(defn calendar-data [p leagues]
  "return a map of calendar data for a single player 
	 for all the leagues they play in"
  {
    :player p
    :events ;; all events from all leagues player is in
      (for [l leagues
             :when (plays-in? (:id p) l)]
         (events p l))})


(defn write-cal-file![filename calendar]
  (println filename)
  (log/info "Generating" filename)
  (spit filename calendar))

(defn event->ical [e]
  (println e)
  [:VEVENT
     [:UID :TODO]
     [:DTSTART :TODO]
     [:DTEND :TODO]
])

(defn data->ical [d] 
  (ical/write-object 
     [:VCALENDAR 
       [:VERSION "2.0"
       (for [event (:events d)]
          (event->ical event))]]))

(defn calendar [p leagues]
  (-> 
    (calendar-data p leagues) ;; calcuate the actual data
    ;; TODO: remove this? Seems unnecessary since this now returns data rather than ical
    ;; rmp:  this is still the function that converts the map of data to the specific format needed as input to ical/write-object
    (data->ical))) ;; convert data to icalendar format

(defn pid->player [pid players]
  (conj { :id pid } (pid players)))

;; NB: this function (and everything it calls) is unit testable
(defn calendars 
  "create a lazy sequence of a calendar for each player"
  [players leagues]
  (for [pid (all-players leagues)]
		(calendar (pid->player pid players) leagues)))

(defn cal->filename [dir calendar]
  "file name to use for this calendar"
	(when-let [pid (get-in calendar [:player :id])]
    (log/info "Processing pid" pid)
	  (format "%s/%s.ics" dir (name pid))))

(defn output-all-calendars! [dir players leagues]
	(doseq ;; same syntax as "for" except it evaluates
		[calendar (calendars players leagues)]
      (when-let [filename (cal->filename dir calendar)]
       (write-cal-file! filename calendar))))

;; TODO: integrate better CLI options using: https://github.com/clojure/tools.cli
(defn -main
  ""
  [& args]
  (let [year "2015" ;; TODO: parse from arguments
        data (str  "data-" year) 
        dir "ical" ] 
  ;;(println data)
  ;; dummy change
  ;; (require '(gvttc-ical data-2015)) ;; TODO: get dynamic loading to work
  (.mkdir (java.io.File. dir)) ;; spit fails if the folder doesn't exist TODO: figure out a strategy
  (output-all-calendars! dir data/players data/leagues)
  ))