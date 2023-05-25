package com.example.tutorial;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    public static ObservableList<Vocab> vocab = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("login-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Vocab.Me"); //name of the program
        stage.setScene(scene);
        stage.show();
    }

    private static Scene scene;
    static void setRoot(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource(fxml + ".fxml"));
        scene.setRoot(fxmlLoader.load()); //change view to the selected fxml file
    }

    public static void main(String[] args) {
        launch();
    }
}
