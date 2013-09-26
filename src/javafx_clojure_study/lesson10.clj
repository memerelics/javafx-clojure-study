(ns javafx-clojure-study.lesson10 ;;{{{
  (:require [clojure.java.io :as io])
  (:import (java.text SimpleDateFormat)
           (java.lang Integer)
           (javafx.util.converter IntegerStringConverter)
           (javafx.util Callback)
           (javafx.util StringConverter)
           (javafx.application Application)
           (javafx.application Platform)
           (javafx.beans.value ChangeListener)
           (javafx.collections ObservableList)
           (javafx.collections FXCollections)
           (javafx.collections ListChangeListener)
           (javafx.concurrent Worker)
           (javafx.concurrent Worker$State) ;; Worker/Stateとかconcurrent.Worker StateではNG
           (javafx.event ActionEvent EventHandler)
           (javafx.event EventHandler)
           (javafx.geometry Pos)
           (javafx.geometry Insets)
           (javafx.fxml FXMLLoader)
           (javafx.scene Scene)
           (javafx.scene.control Button)
           (javafx.scene.control TextField)
           (javafx.scene.layout BorderPane)
           (javafx.scene.layout StackPane)
           (javafx.scene.text Font)
           (javafx.geometry Pos)
           (javafx.scene.text FontPosture)
           (javafx.scene.text FontWeight)
           (javafx.scene.text Text)
           (javafx.scene.web WebView)
           (javafx.scene.web WebEngine)
           (javafx.scene.chart PieChart)
           (javafx.scene.chart PieChart$Data)
           (javafx.scene.chart NumberAxis)
           (javafx.scene.chart LineChart)
           (javafx.scene.chart XYChart)
           (javafx.scene.chart XYChart$Series)
           (javafx.scene.chart XYChart$Data)
           (javafx.stage Stage)
           (com.memerelics.javafx_clojure_study MyObjectBinding))) ;;}}}

(gen-class
  :name "Lesson10"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)


(defn jfx-start [this ^Stage stage]
  (let [
        ;;; 固定URL ;;;
        ;; root (BorderPane.)
        ;; scene (Scene. root 600 400)
        ;; webView (WebView.)
        ;; engine (.getEngine webView)
        ;; button (Button. "DOM")

        ;;; location bar ;;;
        ;; NOTE: use Controller. this cause some importunate problems
        ;; root (-> "javafx_clojure_study/BrowserView.fxml" io/resource FXMLLoader/load)
        ;; scene (Scene. root)
        ;; urlField (.lookup root "urlField") ;; findElementByIdとかいろいろ検討したけどlookupでよかった
        ;; webView (WebView.)
        ;; engine (.getEngine webView)
        ;; worker (.getLoadWorker engine)

        ;;; Chart ;;;
        root (StackPane.)
        scene (Scene. root)
        ;; pie
        pieChart (PieChart.)
        ;; line
        xAxis (doto (NumberAxis. 0 12.5 1)
                    (.setLabel "Number of Month"))
        yAxis (doto (NumberAxis.)
                    (.setLabel "Temprature"))
        lineChart (doto (LineChart. xAxis yAxis)
                        (.setTitle "Average Temprature"))
        tokyoTemp (doto (XYChart$Series.)
                        (.setName "Tokyo"))
        ]

    ;;; 固定URL ;;;
    ;; (.load engine "https://www.evernote.com/OAuth.action")
    ;; (doto root
    ;;   (.setTop button)
    ;;   (.setCenter webView))
    ;; ;; elemsドキュメント http://excalibur.apache.org/apidocs/org/apache/excalibur/xml/xpath/NodeListImpl.html
    ;; ;; 実際は com.sun.webpane.webkit.dom.NodeListImpl
    ;; (.. button (setOnAction (reify EventHandler
    ;;                          (handle [this t]
    ;;                            (let [doc (.getDocument engine)
    ;;                                  elems (.. doc (getElementsByTagName "li"))]
    ;;                              (.. (.item elems 0) (setTextContent "JavaFX")))))))

    ;;; location bar ;;;
    ;;; => BrowserViewConteller.java
    ;; (.. worker stateProperty (addListener
    ;;                           (reify ChangeListener
    ;;                             (changed [this value oldState newState]
    ;;                                      (cond (= newState Worker$State/SUCCEEDED)
    ;;                                              (.. urlField setText (.getLocation engine))
    ;;                                            (= newState Worker$State/FAILED)
    ;;                                              (.. engine (load (str "https://google.co.jp/search?&q=" (.getText urlField)))))))))

    ;;; Chart ;;;
    (.. root getChildren (add pieChart))
    (.. root getChildren (add lineChart))

    ;; PieChart
    (doto pieChart
      (.setTitle "PieChart Lesson10")
      (.setStartAngle 90) ;; 0度ではなく90度からstart
      (-> .getData (.addAll [(PieChart$Data. "A" 30)
                             (PieChart$Data. "B" 10)
                             (PieChart$Data. "C" 20)
                             (PieChart$Data. "D" 50)])))
    ;; LineChart
    (.. tokyoTemp getData (addAll [(XYChart$Data. 1   6.1)
                                   (XYChart$Data. 2   6.5)
                                   (XYChart$Data. 3   9.4)
                                   (XYChart$Data. 4  14.6)
                                   (XYChart$Data. 5  18.9)
                                   (XYChart$Data. 6  22.1)
                                   (XYChart$Data. 7  25.8)
                                   (XYChart$Data. 8  27.4)
                                   (XYChart$Data. 9  23.8)
                                   (XYChart$Data. 10 18.5)
                                   (XYChart$Data. 11 13.3)
                                   (XYChart$Data. 12  8.7)]))
    (.. lineChart getData (add tokyoTemp))

    (doto stage
      (.setTitle "Demo10")
      (.setScene scene)
      .show)))

(defn -main [& args]
    (Application/launch (Class/forName "Lesson10") (into-array String [])))
