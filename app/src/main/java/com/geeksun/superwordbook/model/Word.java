package com.geeksun.superwordbook.model;

public class Word {

    private String wordContent;

    private String phoneticSymbol;

    private String description;

    public Word(){

    }

    public Word(String wordContent, String phoneticSymbol, String description) {
        this.wordContent = wordContent;
        this.phoneticSymbol = phoneticSymbol;
        this.description = description;
    }

    public String getWordContent() {
        return wordContent;
    }

    public void setWordContent(String wordContent) {
        this.wordContent = wordContent;
    }

    public String getPhoneticSymbol() {
        return phoneticSymbol;
    }

    public void setPhoneticSymbol(String phoneticSymbol) {
        this.phoneticSymbol = phoneticSymbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
