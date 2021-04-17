package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public void addAuthor(AuthorDTO authorDTO) {
        authorRepository.save(mappingDTOtoClass.mapAuthorToDTO(authorDTO));
    }

    public List<AuthorDTO> getAllAuthors() {
        List<AuthorDTO> authorsListReturn = new ArrayList<>();
        for (Author author : authorRepository.findAll()) {
            authorsListReturn.add(mappingDTOtoClass.mapToAuthor(author));
        }
        return authorsListReturn;
    }

    public void getAuthor(String authorName) {
        mappingDTOtoClass.mapToAuthor(authorRepository.findAuthorByAuthorName(authorName).get());
    }

    @Transactional
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
//        AuthorDTO authorDTO = new AuthorDTO();
//        authorDTO = mappingDTOtoClass.mapToAuthor(authorRepository.findById(authorID).get());
//        if(authorDTO.getId() == 0 && !authorDTO.getAuthorName().isEmpty()){
//            //authorDTO = mappingDTOtoClass.mapToAuthor(authorRepository.findById(Math.toIntExact(authorID)).get());
//            authorDTO.setAuthorName(authorDTO.getAuthorName());
//            authorDTO.setAuthorDOB(authorDTO.getAuthorDOB());
//        } else {
//            throw new IllegalStateException("something went terribly wrong");
//        }

            Author author = new Author();

//        authorDTO = mappingDTOtoClass.mapToAuthor(authorRepository.findById(authorID).get());
        if(authorRepository.existsById(authorDTO.getId()) && !authorDTO.getAuthorName().isEmpty()){
            //authorDTO = mappingDTOtoClass.mapToAuthor(authorRepository.findById(Math.toIntExact(authorID)).get());
            author = authorRepository.findById(authorDTO.getId()).get();
            author.setAuthorName(authorDTO.getAuthorName());
            author.setAuthorDOB(authorDTO.getAuthorDOB());
        } else {
            throw new IllegalStateException("something went terribly wrong");
        }
        return mappingDTOtoClass.mapToAuthor(author);

    }


}
