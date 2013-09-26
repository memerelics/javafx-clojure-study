(ns javafx-clojure-study.lesson8 ;;{{{
  (:require [clojure.java.io :as io]
            [clojure.contrib.math :as math])
  (:import (java.text SimpleDateFormat)
           (java.lang Integer)
           (javafx.util.converter IntegerStringConverter)
           (javafx.util Callback)
           (javafx.util StringConverter)
           (javafx.application Application)
           (javafx.application Platform)
           (javafx.collections ObservableList)
           (javafx.collections FXCollections)
           (javafx.collections ListChangeListener)
           (javafx.event ActionEvent EventHandler)
           (javafx.geometry Pos)
           (javafx.geometry Insets)
           (javafx.fxml FXMLLoader)
           (javafx.scene Scene)
           (javafx.scene.control CheckBox)
           (javafx.scene.control ComboBox)
           (javafx.scene.control ListView)
           (javafx.scene.control ListCell)
           (javafx.scene.control Label) ;;
           (javafx.scene.control TableView)
           (javafx.scene.control TableCell)
           (javafx.scene.control TableColumn)
           (javafx.scene.control TextArea)
           (javafx.scene.control Slider) ;;
           (javafx.scene.control MenuBar)
           (javafx.scene.control Menu)
           (javafx.scene.control Button)
           (javafx.scene.control MultipleSelectionModel)
           (javafx.scene.control.cell TextFieldListCell)
           (javafx.scene.control.cell TextFieldTableCell)
           (javafx.scene.control.cell PropertyValueFactory)
           (javafx.scene.control ProgressBar)
           (javafx.scene.layout StackPane)
           (javafx.scene.layout GridPane)
           (javafx.scene.layout Priority)
           (javafx.scene.paint Color)
           (javafx.scene.shape Rectangle)
           (javafx.scene.shape Circle)
           (javafx.scene.text Font)
           (javafx.scene.text FontPosture)
           (javafx.scene.text FontWeight)
           (javafx.scene.text Text)
           (javafx.stage Stage)
           (com.memerelics.javafx_clojure_study MyObjectBinding))) ;;}}}

(gen-class
  :name "Lesson8"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)

(defn jfx-start [this ^Stage stage]
  (let [
        ;;; Stack Circle ;;;
        ;; stackPane (StackPane.)
        ;; scene (Scene. stackPane)
        gridPane (GridPane.)
        scene (Scene. gridPane)
        ]

    ;;; Stack Circle ;;;
    ;; (drawCircles stackPane)
    ;; (doto stackPane
    ;;   (.setPrefSize 200 200)
    ;;   (.setAlignment Pos/TOP_LEFT)) ;; 右上に寄せて描画

    (doto gridPane
      (.setPrefSize 400 300)
      (.setPadding (Insets. 10))
      (.setHgap 10)
      (.setVgap 10))

    ;; 4x5のグリッドにボタンを配置
    ;; doseq: Repeatedly executes body (presumably for side-effects) with bindings and filtering as provided by "for".
    (doseq [i (range 1 5 1)
            j (range 1 6 1)]
      (let [button (Button. (str "Button" i "-" j))]
        (.. gridPane (add button j i))))

    (doto stage
      (.setTitle "Domo7")
      (.setScene scene)
      .show)))

(defn drawCircles [^StackPane stackPane]
  (defn iter [i]
    (if (> i 0)
      (let [circle (Circle. (* (+ 50 i) 10))]
        (doto circle (.setFill (Color/color (- 1.0 (/ i 10.0))
                                            0
                                            (/ i 10.0))))
        (.. stackPane getChildren (add circle))
        (iter (- i 1)))))
    (iter 10))



(defn -main [& args]
    (Application/launch (Class/forName "Lesson8") (into-array String [])))

