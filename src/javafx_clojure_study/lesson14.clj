(ns javafx-clojure-study.lesson14 ;;{{{
  (:require [clojure.java.io :as io])
  (:import (javafx.application Application Platform)
           (javafx.animation Animation)
           (javafx.animation RotateTransition)
           (javafx.animation Interpolator)
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
           ;;; 非同期
           (java.util.concurrent Executors)
           )) ;;}}}

(gen-class
  :name "Lesson14"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)

(defn jfx-start [this ^Stage stage]
  (let [image (ImageView. (Image. "javafx_clojure_study/lain.jpg"))
        transition (doto (RotateTransition. (Duration/millis 2000) image)
                     (.setToAngle 360)
                     (.setInterpolator Interpolator/LINEAR)
                     (.setCycleCount Animation/INDEFINITE)
                     (.play))
        service (Executors/newSingleThreadExecutor) ;; 非同期にやるversion...で使うスレッドプール
        button (Button. "Long term task")
        root (doto (BorderPane.)
               (.setPadding (Insets. 10.0))
               (.setCenter image)
               (.setTop (doto button
                          (.setOnAction (reify EventHandler
                                          (handle [this t] ;; thisが必要な点がJavaと違ってハマるなー
                                            ;;; メインスレッドで時間のかかる処理をやる >>> ;;;
                                            ;; (Thread/sleep 5000) ;; sleepそのまんま.

                                            ;;; 非同期にやるversion. >>> ;;;
                                            (.. button (setDisable true))
                                            (let [task (reify Runnable (run [this]
                                                                         (Thread/sleep 5000)
                                                                         ;; runの中からPlatform.runLaterで入れ後Runnable!!
                                                                         (Platform/runLater (reify Runnable (run [this] (.. button (setDisable false)))))
                                                                         ))]
                                              (.. service (submit task)))
                                            ))))))
        scene (Scene. root 300 250)
        ]
    (doto stage
      (.setTitle "Long Time Task Demo")
      (.setScene scene)
      .show)))




(defn -main [& args]
    (Application/launch (Class/forName "Lesson14") (into-array String [])))

