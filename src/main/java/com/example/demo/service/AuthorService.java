package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private MappingDTOtoClass mappingDTOtoClass;

    @Autowired
    public AuthorService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void addAuthor(AuthorDTO authorDTO) {
        authorRepository.save(mappingDTOtoClass.mapAuthorToDTO(authorDTO));
    }


}
