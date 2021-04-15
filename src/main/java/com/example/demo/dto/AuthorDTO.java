package com.example.demo.dto;

import javax.persistence.*;
import java.time.LocalDate;


public class AuthorDTO {

    private Long id;
    private String authorName;
    private LocalDate authorDOB;

    public AuthorDTO() {
    }

    public AuthorDTO(String authorName, LocalDate authorDOB) {
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
