(ns javafx-clojure-study.lesson9 ;;{{{
  (:require [clojure.java.io :as io])
  (:import (javafx.application Application)
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
           (javafx.stage Stage))) ;;}}}

(gen-class
  :name "Lesson9"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)


(defn jfx-start [this ^Stage stage]
  (let [
        root (-> "javafx_clojure_study/CounterView.fxml" io/resource FXMLLoader/load)
        scene (Scene. root)
        ]

    (doto stage
      (.setTitle "Demo9")
      (.setScene scene)
      .show)))


(defn -main [& args]
    (Application/launch (Class/forName "Lesson9") (into-array String [])))


