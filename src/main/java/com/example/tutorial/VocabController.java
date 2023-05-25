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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class VocabController {
    public TextField cNewWordTxt;
    public TextArea cDefinitionTxt;
    public TextField cTranslationTxt;
    public TextField cPronunciationTxt;
    public TextField cLinksTxt;

    public TableView contactsTable;

    @FXML
    private Label welcomeText;

    public TableColumn<Vocab, String> vocabWord = new TableColumn<>("Word"); //name/label of column

    @FXML
    protected void onHelloButtonClick() throws IOException {
        StartApplication.setRoot("vocab-view");
    }

    public void initialize() {

        loadVocab(); //load words from the vocabulary list

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
                public int compare(Vocab vocab1, Vocab vocab2) { //to sort in alphabetical order, compare first letter of words
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
                System.out.println("Already exists"); //to not save duplicates
            }
        }

        if(exists == false) {
            StartApplication.vocab.add(new Vocab(cNewWordTxt.getText(), cDefinitionTxt.getText(), cTranslationTxt.getText(), cPronunciationTxt.getText(), cLinksTxt.getText()));
        } //save new word and all its aspects (definition, translation, pronunciation, links)

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try(FileWriter writer = new FileWriter("vocab.json")) {
                gson.toJson(StartApplication.vocab, writer);
                System.out.println("Saved. "); //save to GSON file
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
            StartApplication.vocab.removeAll(); //remove word from list if delete button is clicked
            StartApplication.vocab=tempVocab;
            contactsTable.setItems(StartApplication.vocab);
        }

    public void searchBtn(ActionEvent actionEvent) throws Exception {
        String word = cNewWordTxt.getText();
        String url = ("https://api.dictionaryapi.dev/api/v2/entries/en_US/" + word); //to search definition in dictionary API
        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //optional default is GET
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(response.toString());
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        JSONArray meaningsArray = (JSONArray) jsonObject.get("meanings");
        JSONObject meaningsObject = (JSONObject) meaningsArray.get(0);

        JSONArray definitionsArray = (JSONArray) meaningsObject.get("definitions");
        JSONObject definitionsObject = (JSONObject) definitionsArray.get(0);

        String definition = (String) definitionsObject.get("definition");
        cDefinitionTxt.setText(definition); //set definition in definition box
    }

    public void signOutBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("login-view");
    } //change view to login page if sing out button is clicked

    public void startBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("play-view");
    } //change view to game page if game button is clicked
}