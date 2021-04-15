package com.example.demo.mapping;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.Objects;


public class MappingDTOtoClass {

    public BookDTO mapToBookDTO (Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setBookName(book.getBookName());
        bookDTO.setBookDescription(book.getBookDescription());
        bookDTO.setAuthorsIDsDTOList(Collections.singletonList(book.getAuthorsList().toString()));
        return bookDTO;
    }

    public Book mapToDTOBook (BookDTO bookDTO) {
        Book book = new Book();
        book.setBookName(bookDTO.getBookName());
        book.setBookDescription(bookDTO.getBookDescription());
        //book.setAuthorsList(bookDTO.get);
        return book;
    }


}
