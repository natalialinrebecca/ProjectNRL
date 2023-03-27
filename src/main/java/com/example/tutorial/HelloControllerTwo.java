package com.example.tutorial;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloControllerTwo {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {welcomeText.setText("No return :(");
    }
}