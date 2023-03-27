package com.example.tutorial;

public class Vocab {

    String newWord;
    String definition;
    String translation;
    String pronunciation;
    String links;


    public Vocab(String newWord, String definition, String translation, String pronunciation, String links) {
        this.newWord = newWord;
        this.definition = definition;
        this.translation = translation;
        this.pronunciation = pronunciation;
        this.links = links;

    }

    public String getNewWord() {
        return newWord;
    }

    public void setNewWord(String newWord) {this.newWord = newWord;}

    public String getDefinition() {return definition;}

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

}
