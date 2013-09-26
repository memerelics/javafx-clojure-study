package com.memerelics.javafx_clojure_study;

import javafx.scene.control.ListCell;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ColorListCell extends ListCell<String> {

    @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty) {
                Rectangle rect = new Rectangle(20, 20);
                rect.setFill(Color.web(item));
                setGraphic(rect);
                setText(item);
            }
        }
}
