package com.example.demo.dto;


import java.util.List;

public class BookDTO {


    private int id;
    private String bookName;
    private String bookDescription;
    private List<String> authorsIDsDTOList;

    public BookDTO() {
    }

    public BookDTO(int id, String bookName, String bookDescription, List<String> authorsIDsDTOList) {
        this.id = id;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
        this.authorsIDsDTOList = authorsIDsDTOList;
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

    public List<String> getAuthorsIDsDTOList() {
        return authorsIDsDTOList;
    }

    public void setAuthorsIDsDTOList(List<String> authorsIDsDTOList) {
        this.authorsIDsDTOList = authorsIDsDTOList;
    }
}
