package com.example.tutorial;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;


public class GameController {


    private void loadVocab() {
        //load vocab from saved file.
        //Open and read Json for any previously saved data.

        Gson gson = new Gson();
        try (Reader reader = new FileReader("vocab.json")) {
            //convert JSON file to Java Object
            ArrayList<Vocab> imports = gson.fromJson(reader, new TypeToken<ArrayList<Vocab>>() {
            }.getType());

            StartApplication.vocab = FXCollections.observableArrayList(imports);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    TextField let1;
    @FXML
    TextField let2;
    @FXML
    TextField let3;
    @FXML
    TextField let4;
    @FXML
    TextField let5;
    @FXML
    TextField let6;
    @FXML
    TextField let7;
    @FXML
    TextField let8;
    @FXML
    TextField input;
    @FXML
    Label letter_count;
    @FXML
    Label points;
    @FXML
    Label wrongRight;

    TextField[] letters = {let1,let2,let3,let4,let5,let6,let7,let8};
    public void initialize(){
    }

    public static String getRandom() {
        Random random = new Random();
        String randomWord = StartApplication.vocab.get(random.nextInt(StartApplication.vocab.size())).getNewWord();
        return randomWord;
    }

    public void GiveNumLetters(){
        String numLetters = String.valueOf(randomWord.length());
        letter_count.setText(numLetters);
    }

    int guessed = 0;
    int score = 0;

    public void CheckInput(){
        String str = input.getText();
        if (randomWord.contains(str)) {
            int index = 0;
            for(int i=0; i<randomWord.length(); i++) {
                char c = randomWord.charAt(i);

                if (String.valueOf(c).equals(str)) {
                    setLetter(index, Character.toString(c));
                    wrongRight.setText("Right!");
                    score += 100;
                    String scoreText = String.valueOf(score);
                    points.setText(scoreText);
                    guessed++;
                    System.out.println(guessed + " out of " + randomWord.length());

                    if(guessed == randomWord.length()) {
                        wrongRight.setText("Done!");
                    }
                }
                index++;
            }
        }
        else {
            wrongRight.setText("Wrong");
            score -= 10;
            String scoreText = String.valueOf(score);
            points.setText(scoreText);
        }
    }
    public void setLetter(int index,String str){
        if(index==0)
            let1.setText(str);
        else if(index==1)
            let2.setText(str);
        else if(index==2)
            let3.setText(str);
        else if(index==3)
            let4.setText(str);
        else if(index==4)
            let5.setText(str);
        else if(index==5)
            let6.setText(str);
        else if(index==6)
            let7.setText(str);
        else if(index==7)
            let8.setText(str);
    }

    public void signOutBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("login-view");
    }

    public void myVocabBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("vocab-view");
    }

    String randomWord;
    public void changeWordBtn(ActionEvent actionEvent) throws IOException {
        randomWord = getRandom();
        GiveNumLetters();
        let1.setText("");
        let2.setText("");
        let3.setText("");
        let4.setText("");
        let5.setText("");
        let6.setText("");
        let7.setText("");
        let8.setText("");
        input.setText("");
    }
}
