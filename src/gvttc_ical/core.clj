(ns gvttc-ical.core
	(:require 
    [clojure.tools.cli :refer [parse-opts]]
    [taoensso.timbre :as log]
		[gvttc-ical.data-2015 :as data] ;; get dynamic loading to work
    [clj-ical.format :as ical]
  )
  (:gen-class))

(defn plays-in? [pid league]
  (let [r (some #{pid} (flatten (:teams league)))]
		;;(println "plays-in?" r "<-" pid league)  ;; seem to have problems with this function
    r))

(defn all-players [all-leagues]
  (->> (map :teams data/leagues) flatten distinct (filter #(not (nil? %)))))

(defn match [p league event]
   "returns information about this match"
  ;; (println "event: " event)
  (let
    [start-time (fn [d] (println "d: " (class d)) (. d setTime (:start-time league)))]
    { ;;:start-time (start-time (key event))
      :format :todo
      :vs-team :todo
      :vs-players [:todo :todo :todo :todo]
    }
  ))

(defn matches [p league]
  (for [e (:events league)]
    (match p league e)))

(defn calendar-data [p leagues]
  "return a map of calendar data for a single player 
	 for all the leagues they play in"
  (let [all-matches ;; all matches from all leagues player is in
       (for [l leagues :when (plays-in? p l)]
			     (matches p l))]
  {
    :player p
    :matches (apply concat all-matches) ;; flatten only one level deep
  }))


(defn write-cal-file![filename calendar]
  (println filename)
  (log/info "Generating" filename)
  (spit filename calendar))

(defn match->ical [e]
  (println e)
  [:VEVENT
     [:UID :TODO]
     [:DTSTART :TODO]
     [:DTEND :TODO]
])

(defn matches->ical [d] 
  (ical/write-object 
     [:VCALENDAR 
       [:VERSION "2.0"
       (for [match (:matches d)]
          (match->ical match))]]))

(defn calendar [p leagues]
  "create a calendar of all the matches a player will play across all leagues"
  (-> 
    (matches p leagues) ;; calcuate the actual matches
    ;; TODO: remove this? Seems unnecessary since this now returns data rather than ical
    ;; rmp:  this is still the function that converts the map of data to the specific format needed as input to ical/write-object
    (matches->ical))) ;; convert match data to icalendar format

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