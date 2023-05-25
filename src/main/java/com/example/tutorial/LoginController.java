package com.example.tutorial;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class LoginController {
    public TextField cUsernameTxt;

    @FXML
    public ObservableList<Login> login = FXCollections.observableArrayList();
    public Label wrongLogin;

public void initialize() throws IOException {

}
    @FXML
    private void checkLogin() throws IOException { //check username
        if(cUsernameTxt.getText().equals(cUsernameTxt.getText())) {
            wrongLogin.setText("Welcome!");
            StartApplication.setRoot("vocab-view");
        }

        else if(cUsernameTxt.getText().isEmpty()) {
            wrongLogin.setText("Please enter a username."); //deny
        }

        else {
            wrongLogin.setText("Wrong username"); //deny
        }
    }
}
