package com.memerelics.javafx_clojure_study;

import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;

public class FontSelViewController implements Initializable {

    @FXML
    private ComboBox<String> familyCombo;
    @FXML
    private ComboBox<String> styleCombo;
    @FXML
    private ComboBox<Integer> sizeCombo;
    @FXML
    private Text text;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 選択項目を指定する
        styleCombo.getSelectionModel().selectFirst();
        sizeCombo.getSelectionModel().select(1);
    }
}
