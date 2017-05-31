package com.example.youssefalaa.booklistingapp;

/**
 * Created by youssef alaa on 30/05/2017.
 */

public class Book {
    private String publisher;
    private String publishedDate;
    private String description;
    private String title;
    private String author;

    public Book(String title, String publisher, String publishedDate, String description, String author) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;

    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}