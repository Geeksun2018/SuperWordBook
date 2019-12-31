package com.geeksun.superwordbook.model;

import java.util.List;

public class WordItem {

        private String wordContent;

        private String AmericanVoice;

        private String AmericanSignal;

        private String EnglishVoice;

        private String EnglishSignal;

        private List<String> explains;

    public WordItem(String wordContent, String americanVoice, String americanSignal, String englishVoice, String englishSignal, List<String> explains) {
        this.wordContent = wordContent;
        AmericanVoice = americanVoice;
        AmericanSignal = americanSignal;
        EnglishVoice = englishVoice;
        EnglishSignal = englishSignal;
        this.explains = explains;
    }

    public String getWordContent() {
        return wordContent;
    }

    public void setWordContent(String wordContent) {
        this.wordContent = wordContent;
    }

    public String getAmericanVoice() {
        return AmericanVoice;
    }

    public void setAmericanVoice(String americanVoice) {
        AmericanVoice = americanVoice;
    }

    public String getAmericanSignal() {
        return AmericanSignal;
    }

    public void setAmericanSignal(String americanSignal) {
        AmericanSignal = americanSignal;
    }

    public String getEnglishVoice() {
        return EnglishVoice;
    }

    public void setEnglishVoice(String englishVoice) {
        EnglishVoice = englishVoice;
    }

    public String getEnglishSignal() {
        return EnglishSignal;
    }

    public void setEnglishSignal(String englishSignal) {
        EnglishSignal = englishSignal;
    }

    public List<String> getExplains() {
        return explains;
    }

    public void setExplains(List<String> explains) {
        this.explains = explains;
    }
}
