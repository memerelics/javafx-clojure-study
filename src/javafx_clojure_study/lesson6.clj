(ns javafx-clojure-study.lesson6 ;;{{{
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
           (javafx.beans.binding Bindings)
           (javafx.beans.binding ObjectBinding)
           (javafx.beans.property SimpleDoubleProperty)
           (javafx.beans.value ObservableValue)
           (javafx.beans.value ChangeListener)
           (javafx.event ActionEvent EventHandler)
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
           (javafx.scene.control MultipleSelectionModel)
           (javafx.scene.control.cell TextFieldListCell)
           (javafx.scene.control.cell TextFieldTableCell)
           (javafx.scene.control.cell PropertyValueFactory)
           (javafx.scene.control ProgressBar)
           (javafx.scene.layout VBox)
           (javafx.scene.layout BorderPane)
           (javafx.scene.layout Priority)
           (javafx.scene.paint Color)
           (javafx.scene.shape Rectangle)
           (javafx.scene.text Font)
           (javafx.scene.text FontPosture)
           (javafx.scene.text FontWeight)
           (javafx.scene.text Text)
           (javafx.stage Stage)
           (com.memerelics.javafx_clojure_study MyObjectBinding))) ;;}}}

(gen-class
  :name "Lesson6"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)


(defn jfx-start [this ^Stage stage]
  (let [root (VBox.)
        scene (Scene. root 600 400)
        slider (Slider. 0 100 0)
        label (Label. "0")
        value (SimpleDoubleProperty.) ;; model. ただのdoubleだけど薄くwrapしてPropertyにしてる
        bar (ProgressBar.)
        ;; bidirectional binding ;;
        area1 (TextArea.)
        area2 (TextArea.)
        ;; 低レベルbind ;;
        rectangle (Rectangle. 200 100)
        slider1 (Slider. 0 1.0 0.5)
        slider2 (Slider. 0 1.0 0.5)
        slider3 (Slider. 0 1.0 0.5)
        ]
    (.. slider (setShowTickLabels true))
    ;; 即座に反映されるChangeListener ;;;
    (.. slider valueProperty (addListener
                               (reify ChangeListener
                                 (changed [this ov old new]
                                    (.. label (setText (str (math/floor new))))))))

    ;; bindで値同士を同期させる(この例ではわからないけど遅延評価). ;;;
    ;;   point1: 普通に(/ ...) じゃ割り算できない
    ;;   point2: ここでもDouble化しないとだめ
    (.. value (bind (Bindings/divide (.valueProperty slider) (Double. 100.0))))
    (.. bar progressProperty (bind value))

    ;;; 双方向バインド(bidirectional binding) ;;;
    (.. area1 textProperty (bindBidirectional (.textProperty area2)))
    ;; (map (fn [area] (.setPrefRowCount area 2)) [area1 area2]) うまくいかんかった
    (.setPrefRowCount area1 2)
    (.setPrefRowCount area2 2)

    ;;; 低レベルバインド ;;;
    ;; 無名クラスでObjectbinding<Color>の挙動を定義
    (.. rectangle fillProperty (bind (MyObjectBinding. slider1 slider2 slider3)))

    (doto (.getChildren root)
      (.add slider) (.add label)
      (.add bar) (.add area1) (.add area2)
      (.add rectangle) (.add slider1) (.add slider2) (.add slider3))

    (doto stage
      (.setTitle "Domo6")
      (.setScene scene)
      .show)))

(defn -main [& args]
    (Application/launch (Class/forName "Lesson6") (into-array String [])))
