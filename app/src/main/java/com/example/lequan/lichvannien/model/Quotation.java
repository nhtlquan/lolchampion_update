package com.example.lequan.lichvannien.model;

public class Quotation {
    private String author;
    private int id;
    private String quotation;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuotation() {
        return this.quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
