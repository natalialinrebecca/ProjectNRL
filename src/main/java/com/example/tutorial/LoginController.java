package com.example.tutorial;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class LoginController {
    public TextField cUsernameTxt;

    @FXML
    public ObservableList<Login> username = FXCollections.observableArrayList();
    public Label wrongLogin;

public void initialize() throws IOException {
    loadUsername();
}
    @FXML
    private void loadUsername() throws FileNotFoundException {
        //load username from saved file.
        Gson gson = new Gson();
        try (Reader reader = new FileReader("username.json")) {
            ArrayList<Login> imports = gson.fromJson(reader, new TypeToken<ArrayList<Login>>() {
            }.getType());
            username = FXCollections.observableArrayList(imports);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkLogin() throws IOException { //check username

        if(cUsernameTxt.getText().equals(username.get(0).getUsername())) {
            wrongLogin.setText("Welcome!");
            StartApplication.setRoot("vocab-view");
        } else if(cUsernameTxt.getText().isEmpty()) {
            wrongLogin.setText("Please enter a username."); //deny
        } else {
            wrongLogin.setText("Wrong username"); //deny
        }
    }
}
