(ns gvttc-ical.data-2015)
;; TODO: consider switching to using EDN

;; 2016 schedules online
;; Monday: https://drive.google.com/file/d/0B8etdrrcPnjHcjFCeDlSQ2wxQzA/view
;; Tuesday: https://drive.google.com/file/d/0B8etdrrcPnjHcjFCeDlSQ2wxQzA/view
;; Wednesday: https://drive.google.com/file/d/0B8etdrrcPnjHcjFCeDlSQ2wxQzA/view

(def players 
  {
   :amaronian {:name "Andre Maronian" }
   :rprescott {:name "Ralph Prescott" }
   :rmack {:name "Ray Mack" }
   ;; ...
})

(def monday-league  {
   :type :monday
   :start-time 18 ;; 6PM
   :end-time 22 ;; 10PM
	 :events (sorted-map ;; not sure if a sorted map is actually is needed
		#inst "2015-10-06" { :matches [[4 3] [8 10] [11 7] [1 6] [5 2] [9 12] ] }
    #inst "2015-10-05" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-10-12" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-10-19" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-11-02" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-11-09" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-11-16" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-11-23" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-11-30" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-12-07" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-12-14" { :matches [[] [] [] [] [] [] ] }
		;;#inst "2015-12-21" { :type :off :text "Merry Christmas"}
	 )
;; ...
           
	 :teams 
   [
     nil ;; team 0
     [:amaronian :dfreeeman :ebizari :gmaronian] ;; team 1
		 [] ;; team 2
		 [] ;; team 3
		 [] ;; team 4
		 [] ;; team 5
		 [:rprescott :tmchang :hshi :ddwight] ;; team 6
		 [] ;; team 7
		 [] ;; team 8
		 [] ;; team 9
		 [] ;; team 10
		 [] ;; team 11
	 ]
   :tables 
   [
		"1 & Half of 2"
		"3 & Half of 2"
		"4 & Half of 5"
		"6 & Half of 5"
		"7 & 8"
    :bye
	 ]
})

(def tuesday-league  
  {
   :type :tuesday
   :teams 
   [
     nil ;; team 0
     [] ;; team 1
		 [] ;; team 2
		 [:pyu :rprescott :bstein] ;; team 3
   ]
})

;; https://drive.google.com/file/d/0B8etdrrcPnjHcjFCeDlSQ2wxQzA/view
(def wednesday-league 
  {
   :type :wednesday })

(def leagues [monday-league tuesday-league wednesday-league])
   