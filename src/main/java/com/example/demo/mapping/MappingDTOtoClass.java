package com.example.demo.mapping;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BookDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;


public class MappingDTOtoClass {

    private AuthorService authorService;

    public BookDTO mapToBookDTO (Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setBookName(book.getBookName());
        bookDTO.setBookDescription(book.getBookDescription());
        //bookDTO.setAuthorsIDsDTOList(book.getAuthorsList());

        List<Author> authorsList = book.getAuthorsList();
        List<String> authorIDs = new ArrayList<>(authorsList.size());
        for(Author author : authorsList){
            authorIDs.add(String.valueOf(author.getId()));
        }
        bookDTO.setAuthorsIDsDTOList(authorIDs);
        return bookDTO;
    }

    public Book mapToDTOBook (BookDTO bookDTO) {
        Book book = new Book();
        book.setBookName(bookDTO.getBookName());
        book.setBookDescription(bookDTO.getBookDescription());

        List<String> authorIDs = bookDTO.getAuthorsIDsDTOList();
        List<Author> authorsList = new ArrayList<>(authorIDs.size());
        for(String authorID : authorIDs) {
            authorsList.add(authorService.getAuthorById(authorID));
        }
        book.setAuthorsList(authorsList);

        return book;
    }

    public AuthorDTO mapToAuthor (Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setAuthorName(author.getAuthorName());
        authorDTO.setAuthorDOB(author.getAuthorDOB());
        return authorDTO;
    }

    public Author mapAuthorToDTO (AuthorDTO authorDTO){
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setAuthorName(authorDTO.getAuthorName());
        author.setAuthorDOB(authorDTO.getAuthorDOB());
        return author;
    }
}
