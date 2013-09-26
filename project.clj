(defproject javafx-clojure-study "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :local-repo "m2repo"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [local.oracle/jfxrt "2.2.25"]]
  :java-source-paths ["src/java"]
  :aot [javafx-clojure-study.lesson
        javafx-clojure-study.lesson6
        javafx-clojure-study.lesson8
        javafx-clojure-study.lesson10
        javafx-clojure-study.lesson14
        javafx-clojure-study.lesson142]
  :main javafx-clojure-study.lesson) ;; change main namespace to launch each lesson result
