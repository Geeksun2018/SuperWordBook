package com.geeksun.superwordbook.model;

public class SearchAmount implements Comparable<SearchAmount>{
    private int id;

    private int uid;

    private String date;

    private int amount;

    public SearchAmount(){

    }

    public SearchAmount(int id, int uid, String date, int amount) {
        this.id = id;
        this.uid = uid;
        this.date = date;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(SearchAmount searchAmount) {
        return this.date.compareTo(searchAmount.date);
    }
}
