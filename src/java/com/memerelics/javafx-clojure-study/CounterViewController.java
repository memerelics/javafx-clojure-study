package com.memerelics.javafx_clojure_study;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CounterViewController {
    private int count = 0;

    @FXML
    private Label countLabel;

    @FXML
    private void action(ActionEvent event) {
        count++;
        countLabel.setText(String.valueOf(count));
    }
}
