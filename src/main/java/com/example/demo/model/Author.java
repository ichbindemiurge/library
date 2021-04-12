package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String authorName;
    private LocalDate authorDOB;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> book;



    public Author() {
    }

    public Author(String authorName, LocalDate authorDOB) {
        this.authorName = authorName;
        this.authorDOB = authorDOB;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
