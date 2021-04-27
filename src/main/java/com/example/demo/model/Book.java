package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String bookName;
    private String bookDescription;

    @ManyToMany(mappedBy = "booksList")
    private List<Author> authorsList = new ArrayList<>();



    public Book() {
    }

    public Book(String bookName, String bookDescription, List<Author> authorsList) {
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.authorsList = authorsList;
    }

    public void addAuthor(Author authorMap){
        authorsList.add(authorMap);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public Book setAuthorsList(List<Author> author) {
        this.authorsList = author;
        return this;
    }

}
