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
import java.util.Collections;
import java.util.Comparator;

public class VocabController {
    public TextField cNewWordTxt;
    public TextField cDefinitionTxt;
    public TextField cTranslationTxt;
    public TextField cPronunciationTxt;
    public TextField cLinksTxt;

    public TableView contactsTable;

    @FXML
    private Label welcomeText;

    public TableColumn<Vocab, String> vocabWord = new TableColumn<>("Word");

    @FXML
    protected void onHelloButtonClick() throws IOException {
        StartApplication.setRoot("vocab-view");
    }

    public void initialize() {

        loadVocab();

        vocabWord.setCellValueFactory(new PropertyValueFactory<Vocab, String>("newWord"));

        contactsTable.getColumns().add(vocabWord);
        contactsTable.setItems(StartApplication.vocab);

        contactsTable.setRowFactory(rowClick -> {
            TableRow<Vocab> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Vocab clickedRow = row.getItem();
                    cNewWordTxt.setText(clickedRow.getNewWord());
                    cDefinitionTxt.setText(clickedRow.getDefinition());
                    cTranslationTxt.setText(clickedRow.getTranslation());
                    cPronunciationTxt.setText(clickedRow.getPronunciation());
                    cLinksTxt.setText(clickedRow.getLinks());
                }
            });
            return row;
        });
    }

    private void loadVocab() {
        //load vocab from saved file.
        //Open and read Json for any previously saved data.

        Gson gson = new Gson();
        try (Reader reader = new FileReader("vocab.json")) {
            //convert JSON file to Java Object
            ArrayList<Vocab> imports = gson.fromJson(reader, new TypeToken<ArrayList<Vocab>>() {
            }.getType());

            Collections.sort(imports, new Comparator<Vocab>() {
                @Override
                public int compare(Vocab vocab1, Vocab vocab2) {
                    return vocab1.getNewWord().compareTo(vocab2.getNewWord());
                }
            });

            StartApplication.vocab = FXCollections.observableArrayList(imports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void saveBtn(ActionEvent actionEvent) {

        boolean exists = false;
        for (Vocab c: StartApplication.vocab) {
            if(c.getNewWord().equals(cNewWordTxt.getText())) {
                exists = true;
                System.out.println("Already exists");
            }
        }

        if(exists == false) {
            StartApplication.vocab.add(new Vocab(cNewWordTxt.getText(), cDefinitionTxt.getText(), cTranslationTxt.getText(), cPronunciationTxt.getText(), cLinksTxt.getText()));
        }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try(FileWriter writer = new FileWriter("vocab.json")) {
                gson.toJson(StartApplication.vocab, writer);
                System.out.println("Saved. ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void delBtn(ActionEvent actionEVent){
             ObservableList<Vocab> tempVocab = FXCollections.observableArrayList();

            for (Vocab v: StartApplication.vocab) {
               if(!v.getNewWord().equals(cNewWordTxt.getText())) {
                  tempVocab.add(v);
               }
            }
            StartApplication.vocab.removeAll();
            StartApplication.vocab=tempVocab;
            contactsTable.setItems(StartApplication.vocab);
        }

    public void signoutBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("login-view");
    }

    public void startBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("play-view");
    }
}