(ns gvttc-ical.core
	(:require 
    [clojure.tools.cli :refer [parse-opts]]
		[gvttc-ical.data-2015 :as data] ;; get dynamic loading to work
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
  {
    :player p
    :events ;; all events from all leagues player is in
       (for [l leagues
             :when (plays-in? (:id p) l)]
         (events p l))})

(defn data->ical [d] 
  d) ;; TODO: really implement this

(defn calendar [p leagues]
  (-> 
    (calendar-data p leagues) ;; calcuate the actual data
    (data->ical))) ;; convert data to icalendar format

(defn pid->player [pid players]
  (conj { :id pid } (pid players)))

;; NB: this function (and everything it calls) is unit testable
(defn all-calendars 
  "create a lazy sequence of the calendar for each player"
  [players leagues]
  (for [pid (all-players leagues)]
		(calendar (pid->player pid players) leagues)))

(defn cal->file [dir calendar]
  "file name to use for this calendar"
	(let [pid (get-in calendar [:player :id])]
	  (format "%s/%s.ics" dir (name pid))))

(defn output-all-calendars! [dir players leagues]
	(doseq 
		[calendar (all-calendars players leagues)
     file (cal->file dir calendar)]
        (println file "=" calendar)
        ;; something like: (spit file calendar)
        ))

;; TODO: integrate better CLI options using: https://github.com/clojure/tools.cli
(defn -main
  ""
  [& args]
  (let [year "2015" ;; TODO: parse from arguments
        data (str  "data-" year) 
        dir "ical" ] 
  (println data)
  ;; dummy change
  ;; (require '(gvttc-ical data-2015)) ;; TODO: get dynamic loading to work
  (output-all-calendars! dir data/players data/leagues)
  ))