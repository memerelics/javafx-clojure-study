package com.memerelics.javafx_clojure_study;

import javafx.beans.binding.ObjectBinding;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class MyObjectBinding extends ObjectBinding {

    private Slider slider1;
    private Slider slider2;
    private Slider slider3;

    public MyObjectBinding(Slider slider1, Slider slider2, Slider slider3) {
        this.slider1 = slider1;
        this.slider2 = slider2;
        this.slider3 = slider3;
        super.bind(slider1.valueProperty(),
                slider2.valueProperty(),
                slider3.valueProperty());
    }

    @Override
    protected Color computeValue() {
        // スライダの値を元にColorオブジェクトを生成
        return Color.color(slider1.valueProperty().get(),
                slider2.valueProperty().get(),
                slider3.valueProperty().get());
    }
}
