package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BookDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class AuthorService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private MappingDTOtoClass mappingDTOtoClass = new MappingDTOtoClass();

    @Autowired
    public AuthorService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void addAuthor(AuthorDTO authorDTO) {
        authorRepository.save(mappingDTOtoClass.authorToDTO(authorDTO));
    }

    @Transactional
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorDTO> authorsListReturn = new ArrayList<>();
        for (Author author : authorRepository.findAll()) {
            authorsListReturn.add(mappingDTOtoClass.DTOToAuthor(author));
        }
        return authorsListReturn;
    }

    @Transactional
    public void getAuthor(String authorName) {
        mappingDTOtoClass.DTOToAuthor(authorRepository.findAuthorByAuthorName(authorName).orElseThrow(
                () -> new IllegalStateException("This author does not exist in the DB"))
        );
    }

    @Transactional
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        Author author;
        if(authorRepository.findById(authorDTO.getId()).isPresent()){
            System.out.println("ID EXISTS, YOU CAN PROCEED");
            author = authorRepository.findById(authorDTO.getId()).get();
            if (!authorDTO.getAuthorName().isEmpty()) {
                author.setAuthorName(authorDTO.getAuthorName());
            }
            if(authorDTO.getAuthorDOB() != null){
                author.setAuthorDOB(authorDTO.getAuthorDOB());
            }
        } else {
            throw new IllegalStateException("something went terribly wrong");
        }
            return mappingDTOtoClass.DTOToAuthor(author);
    }

    @Transactional
    public void deleteAuthor(String authorID){
        Author author = authorRepository.findById(Integer.valueOf(authorID)).orElseThrow(
                () -> new IllegalStateException("This author does not exist in the DB")
        );
        List<Book> linkedBooks = author.getBooksList();
        linkedBooks.removeIf(book -> (book.getAuthorsList().size()==1 && book.getAuthorsList().contains(author)));
        authorRepository.deleteById(Integer.valueOf(authorID));
    }

}
