package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String authorName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate authorDOB;


    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> booksList = new ArrayList<>();

    public Author() {
    }

    public Author(String authorName, LocalDate authorDOB) {
        this.authorName = authorName;
        this.authorDOB = authorDOB;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void addBook(Book book){
        this.booksList.add(book);
    }

    public void removeBook(Book book){
        this.booksList.remove(book);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getAuthorDOB() {
        return authorDOB;
    }

    public void setAuthorDOB(LocalDate authorDOB) {
        this.authorDOB = authorDOB;
    }
}
