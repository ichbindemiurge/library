package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;


public class AuthorDTO {

    private int id;

    private String authorName;


    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate authorDOB;

    public AuthorDTO() {
    }

    public AuthorDTO(String authorName, LocalDate authorDOB) {
        this.authorName = authorName;
        this.authorDOB = authorDOB;
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
