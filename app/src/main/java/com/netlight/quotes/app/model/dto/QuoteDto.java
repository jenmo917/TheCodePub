package com.netlight.quotes.app.model.dto;

public class QuoteDto {
    private String author;
    private String quote;
    private String category;


    public QuoteDto() {
    }

    public QuoteDto(String quote, String author, String category) {
        this.quote = quote;
        this.author = author;
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
