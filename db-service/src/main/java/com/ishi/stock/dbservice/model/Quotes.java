package com.ishi.stock.dbservice.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Quotes {
    private String userName;
    private List<String> quotes;

    public Quotes() {
    }

    public Quotes(String userName, List<String> quotes) {
        this.userName = userName;
        this.quotes = quotes;
    }

    public String getUserName() {
        return userName;
    }
    @Autowired
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getQuotes() {
        return quotes;
    }
    @Autowired
    public void setQuotes(List<String> quotes) {
        this.quotes = quotes;
    }
}
