package com.example.tutorial;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class LoginController {
    public TextField cUsernameTxt;

    @FXML
    public ObservableList<Login> login = FXCollections.observableArrayList();
    public Label wrongLogin;

public void initialize() throws IOException {

}
    private void loadUser() {
        //load user from saved file.
        //Open and read Json for any previously saved data.

        Gson gson = new Gson();
        try (Reader reader = new FileReader("login.json")) {
            //convert JSON file to Java Object
            ArrayList<Login> imports = gson.fromJson(reader, new TypeToken<ArrayList<Login>>() {
            }.getType());
            login = FXCollections.observableArrayList(imports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @FXML
        private void checkLogin() throws IOException {


            if(cUsernameTxt.getText().equals(cUsernameTxt.getText())) {
                wrongLogin.setText("Welcome!");
                StartApplication.setRoot("vocab-view");
            }

            else if(cUsernameTxt.getText().isEmpty()) {
                wrongLogin.setText("Please enter a username.");
            }

            else {
                wrongLogin.setText("Wrong username");
            }
        }
    }
