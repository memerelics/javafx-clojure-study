(ns javafx-clojure-study.lesson142 ;;{{{
  (:require [clojure.java.io :as io]
            [clojure.contrib.io :as cio])
  (:import (java.io InputStreamReader FileInputStream BufferedReader)
           (javafx.application Application Platform)
           (javafx.animation Animation)
           (javafx.animation RotateTransition)
           (javafx.animation Interpolator)
           (javafx.concurrent Task)
           (javafx.event ActionEvent EventHandler)
           (javafx.event EventHandler)
           (javafx.geometry Pos)
           (javafx.geometry Insets)
           (javafx.fxml FXMLLoader)
           (javafx.scene Scene)
           (javafx.scene.control Button)
           (javafx.scene.control TextField)
           (javafx.scene.layout BorderPane)
           (javafx.scene.image Image ImageView)
           (javafx.stage Stage)
           (javafx.util Duration)
           (java.util.concurrent Executors)
           (java.util.concurrent LinkedBlockingQueue)
           )) ;;}}}

(gen-class
  :name "Lesson142"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)


(defn jfx-start [this ^Stage stage]

  (let [root (-> "javafx_clojure_study/FileViewer.fxml" io/resource FXMLLoader/load)
        scene (Scene. root 300 250)
        ;; 非同期関係 ;;
        service (Executors/newSingleThreadExecutor)
        queue (LinkedBlockingQueue.)
        ]

    (defn ^Task FileLoadTask [file]
      (proxy [Task] []
        (call []
          (let [size (.length file)
                readByte (ref 0)]
            (with-open [br (BufferedReader. (InputStreamReader. (FileInputStream. file)))]
              (doseq [line (cio/read-lines br)]
                (println line)
                (dosync (ref-set readByte (+ readByte (.. line getBytes length))))
                (.updateProgress readByte size)
                (.. queue (offer line))
                ))))))

    (doto stage
      (.setTitle "Demo 14-2")
      (.setScene scene)
      .show)))

(defn -main [& args]
    (Application/launch (Class/forName "Lesson142") (into-array String [])))

