;; See: https://github.com/technomancy/leiningen/blob/master/sample.project.clj
;;
(defproject gvttc-ical "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies 
  [
   [org.clojure/clojure "1.8.0"]
   [org.clojure/tools.cli "0.3.5"]
  ]
  :main ^:skip-aot gvttc-ical.core
  :target-path "target/%s"
  :profiles { 
		:uberjar {:aot :all}
		:dev {
		 :dependencies []
     :plugins [[lein-ancient "0.6.10"]]}
   })
