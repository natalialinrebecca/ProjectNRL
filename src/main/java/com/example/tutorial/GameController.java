package com.example.tutorial;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Random;


public class GameController {

    //variables for the GUI
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

    TextField[] letters = {let1,let2,let3,let4,let5,let6,let7,let8}; //a letter for each text box
    public void initialize(){
    }

    public static String getRandom() { //get random word from vocabulary list
        Random random = new Random();
        String randomWord = StartApplication.vocab.get(random.nextInt(StartApplication.vocab.size())).getNewWord();
        return randomWord;
    }

    public void GiveNumLetters(){ //display the number of letters in the word randomly selected
        String numLetters = String.valueOf(randomWord.length());
        letter_count.setText(numLetters);
    }

    int guessed = 0; //int for correct guesses
    int score = 0; //int for user's score

    public void CheckInput(){
        String str = input.getText(); //set the user's input as a string
        if (randomWord.contains(str)) { //check if the word selected contains the user's input (letter guessed)
            int index = 0;
            for(int i=0; i<randomWord.length(); i++) { //check every index of the word selected
                char c = randomWord.charAt(i);

                if (String.valueOf(c).equals(str)) { //if the current index (letter it's looking at) matches the user's input
                    setLetter(index, Character.toString(c)); //if true, display the letter in the text box
                    wrongRight.setText("Right!"); //display "Right!" for affirmation
                    score += 100; //add 100 points to user's score
                    String scoreText = String.valueOf(score); //turn int into string
                    points.setText(scoreText);
                    guessed++; //add 1 to correct guesses
                    System.out.println(guessed + " out of " + randomWord.length()); //keep track of correct guesses

                    if(guessed == randomWord.length()) {
                        wrongRight.setText("Done!"); //Display "Done!" once all the whole word has been guessed correctly
                    }
                }
                index++;
            }
        }
        else if (isInteger(str)) {
            wrongRight.setText("No integers!"); //notify to not use integers
        }
        else {
            wrongRight.setText("Wrong"); //display "Wrong" is the letter guessed is not in the word
            score -= 10; //subtract 10 points from score
            String scoreText = String.valueOf(score);
            points.setText(scoreText); //update score
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void setLetter(int index,String str){ //set one letter of the word for each box in the game
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
        StartApplication.setRoot("login-view"); //go to Log In page
    }

    public void myVocabBtn(ActionEvent actionEvent) throws IOException {
        StartApplication.setRoot("vocab-view"); //go to vocabulary list page
    }

    String randomWord;
    public void changeWordBtn(ActionEvent actionEvent) throws IOException { //restart the game and select a new random word from the list
        randomWord = getRandom();
        GiveNumLetters();
        let1.setText(""); //make text boxes blank again
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
