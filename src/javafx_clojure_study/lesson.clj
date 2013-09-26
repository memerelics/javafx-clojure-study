(ns javafx-clojure-study.lesson ;;{{{
  (:require [clojure.java.io :as io])
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
           (javafx.beans.value ObservableValue)
           (javafx.event ActionEvent EventHandler)
           (javafx.fxml FXMLLoader)
           (javafx.scene Scene)
           (javafx.scene.control CheckBox)
           (javafx.scene.control ComboBox)
           (javafx.scene.control ListView)
           (javafx.scene.control ListCell)
           (javafx.scene.control TableView)
           (javafx.scene.control TableCell)
           (javafx.scene.control TableColumn)
           (javafx.scene.control MenuBar)
           (javafx.scene.control Menu)
           (javafx.scene.control MultipleSelectionModel)
           (javafx.scene.control.cell TextFieldListCell)
           (javafx.scene.control.cell TextFieldTableCell)
           (javafx.scene.control.cell PropertyValueFactory)
           (javafx.scene.layout AnchorPane)
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
           (com.memerelics.javafx_clojure_study ColorListCell)
           (com.memerelics.javafx_clojure_study FontSelViewController)
           (com.memerelics.javafx_clojure_study Album))) ;;}}}

(gen-class
  :name "Lesson"
  :main true
  :prefix "jfx-"
  :extends javafx.application.Application)



(defn jfx-start [this ^Stage stage]
  (let [root (VBox.)
        items (FXCollections/observableArrayList ["Red" "Green" "Blue" "Yellow" "Magenta" "Cyan" "Black" "White"])
        listView (ListView. items) ;; カラの場合: listView (ListView.)
        model (.getSelectionModel listView) ;; 項目選択時にアクション
        selectedItems (.getSelectedItems model)
        scene (Scene. root 600 400)
        ;;; ComboBox ;;;
        families (FXCollections/observableArrayList ["Serif" "SansSerif" "Monospaced" "Dialog" "DialogInput"])
        familyCombo (ComboBox. families)
        styles (FXCollections/observableArrayList ["Plain" "Bold" "Italic" "BoldItalic"])
        styleCombo (ComboBox. styles)
        sizes (FXCollections/observableArrayList [8 12 16 20 24])
        sizeCombo (ComboBox. sizes)
        ;;; Table ;;;
        table (TableView.)
        titleColumn   (doto (TableColumn. "Title") (.setMinWidth 100))
        artistColumn  (doto (TableColumn. "Artist") (.setMinWidth 150))
        releaseColumn (doto (TableColumn. "Release Year") (.setMinWidth 70))
        albums (FXCollections/observableArrayList [ (Album. "Buffalo Springfield" "Buffalo Springfield" 1966)
                                                    (Album. "Last Time Around" "Buffalo Springfield" 1968)
                                                    (Album. "After the Gold Rush" "Neil Young" 1970)
                                                    (Album. "Deja Vu" "Crosby Stills Nash & Young" 1970)
                                                    (Album. "Harvest" "Neil Young" 1972)
                                                    (Album. "Tonight's the Night" "Neil Young" 1975)
                                                    (Album. "Long May You Run" "Neil Young" 1976)
                                                    (Album. "CSN" "Crosby Stills & Nash" 1977)
                                                    (Album. "Rust Never Sleeps" "Neil Young" 1979)
                                                    (Album. "Hawks & Doves" "Neil Young" 1980)
                                                    (Album. "American Dream" "Crosby Stills Nash & Young" 1988)
                                                    (Album. "Harvest Moon" "Neil Young" 1992)])
        ]
    (VBox/setVgrow listView Priority/SOMETIMES)
    (.. root getChildren (add listView))

    ;; 選択したitemをstdoutに流す
    (.. selectedItems (addListener (reify ListChangeListener
                                     (onChanged [this change]
                                                (println (.. change getList))))))

    ;; listをダブルクリックで選択可能に
    (doto listView
      (.setEditable true)
      ;; (.setCellFactory (TextFieldListCell/forListView))
      (.setCellFactory (proxy [Callback] []
                        (call [lst] (ColorListCell.))))

      (.setOnEditCommit (reify EventHandler
                          (handle [this event]
                            (let [index (.. event getIndex)]
                              (.set items index (.. event getNewValue)))))))

    ;;;;;;;;;;;;;;;;
    ;;; ComboBox ;;;
    (doto familyCombo (.setEditable true))
    (.. styleCombo getSelectionModel selectFirst)
    (.. sizeCombo getSelectionModel (select 1))
    (.. root getChildren (add familyCombo))
    (.. root getChildren (add styleCombo))
    (.. root getChildren (add sizeCombo))

    (def text (Text. (str "ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"
                          "abcdefghijklmnopqrstuvwxyz\n"
                          "0123456789")))
    ;; ドキュメントによれば用意されてるはずのAPIが使えない?
    ;; (Font/font "Serif" FontPosture/REGULAR 12)
    ;; (Font/font "Serif" FontWeight/MEDIUM 12) ;; java.lang.IllegalArgumentException: No matching method found: font
    (.. text (setFont (Font/font "Serif" 12))) ;; ok
    (.. root getChildren (add text))
    (def handler (reify EventHandler
                   (handle [this event]
                     (let [family  (.. familyCombo getValue)
                           style   (.. styleCombo getValue)
                           size    (.. sizeCombo getValue)
                           posture (ref FontPosture/REGULAR)
                           weight  (ref FontWeight/MEDIUM)]
                       (dosync
                         (condp = style
                           "Bold"       (ref-set weight FontWeight/BOLD)
                           "Italic"     (ref-set posture FontPosture/ITALIC)
                           "BoldItalic" (doto (ref-set weight FontWeight/BOLD)
                                              (ref-set posture FontPosture/ITALIC))
                           :default))
                       ;; NOTE: convert Long -> Integer!!
                       (.. text (setFont (Font/font family @weight @posture (Integer. size))))))))
    (.. familyCombo (setOnAction handler))
    (.. styleCombo (setOnAction handler))
    (.. sizeCombo (setOnAction handler))

    ;;;;;;;;;;;;;
    ;;; Table ;;;
    (doto table
      (-> .getColumns (.addAll [titleColumn artistColumn releaseColumn]))
      (.setItems albums)
      (.setEditable true))

    (doto titleColumn
      (.setCellFactory (TextFieldTableCell/forTableColumn))
      ;; 編集が完了したらモデルに反映させる
      (.setOnEditCommit (reify EventHandler
                          (handle [this event]
                            (let [album (.. event getTableView getItems
                                            (get (.. event getTablePosition getRow)))]
                              (.. album (setTitle (.getNewValue event))))))))

    (doto releaseColumn
      (.setCellFactory (TextFieldTableCell/forTableColumn (IntegerStringConverter.))))


    (.. titleColumn (setCellValueFactory (PropertyValueFactory. "title")))
    (.. artistColumn (setCellValueFactory (PropertyValueFactory. "artist")))
    (.. releaseColumn (setCellValueFactory (PropertyValueFactory. "release")))

    (.. root getChildren (add table))


    (doto stage
      (.setTitle "Doemo")
      (.setScene scene)
      .show)))

(defn -main [& args]
    (Application/launch (Class/forName "Lesson") (into-array String [])))

