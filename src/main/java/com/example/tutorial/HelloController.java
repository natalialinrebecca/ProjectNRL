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

public class HelloController {
    public TextField cNameTxt;
    public TextField cAgeTxt;
    public TextField cNumberTxt;
    public TableView contactsTable;

    @FXML
    private Label welcomeText;

    public TableColumn<Contact, String> contactsName = new TableColumn<>("Name");
    public ObservableList<Contact> contacts = FXCollections.observableArrayList();

    @FXML
    protected void onHelloButtonClick() throws IOException {
        HelloApplication.setRoot("Hello-Two-view");
    }

    public void initialize() {

        loadContacts();

        contactsName.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));

        contactsTable.getColumns().add(contactsName);
        contactsTable.setItems(contacts);

        contactsTable.setRowFactory(rowClick -> {
            TableRow<Contact> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Contact clickedRow = row.getItem();
                    cNameTxt.setText(clickedRow.getName());
                    cAgeTxt.setText(Integer.toString(clickedRow.getAge()));
                    cNumberTxt.setText(Integer.toString(clickedRow.getNumber()));
                }
            });
            return row;
        });
    }

    private void loadContacts() {
        //load contacts from saved file.
        //Open and read Json for any previously saved data.

        Gson gson = new Gson();
        try (Reader reader = new FileReader("contacts.json")) {
            //convert JSON file to Java Object
            ArrayList<Contact> imports = gson.fromJson(reader, new TypeToken<ArrayList<Contact>>() {
            }.getType());
            contacts = FXCollections.observableArrayList(imports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void saveBtn(ActionEvent actionEvent) {

        boolean exists = false;
        for (Contact c: contacts) {
            if(c.getName().equals(cNameTxt.getText())) {
                exists = true;
                System.out.println("Already exists");
            }
        }

        if(exists == false) {
            contacts.add(new Contact(cNameTxt.getText(), Integer.parseInt(cAgeTxt.getText()), Integer.parseInt(cNumberTxt.getText())));
        }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try(FileWriter writer = new FileWriter("contacts.json")) {
                gson.toJson(contacts, writer);
                System.out.println("Saved. ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}