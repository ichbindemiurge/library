package com.example.demo.service;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDate;
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
        authorRepository.save(mappingDTOtoClass.mapAuthorToDTO(authorDTO));
    }

    @Transactional
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorDTO> authorsListReturn = new ArrayList<>();
        for (Author author : authorRepository.findAll()) {
            authorsListReturn.add(mappingDTOtoClass.mapToAuthor(author));
        }
        return authorsListReturn;
    }

    @Transactional
    public void getAuthor(String authorName) {
        mappingDTOtoClass.mapToAuthor(authorRepository.findAuthorByAuthorName(authorName).get());
    }

    @Transactional
    public Author getAuthorById(String authorID){
        return authorRepository.findById(Integer.valueOf(authorID)).get();
    }

//    @Transactional
//    public AuthorDTO updateAuthor(String authorID, String authorName, LocalDate authorDOB) {
//        Author author;
//        if (mappingDTOtoClass.mapToAuthor(authorRepository.findById(Integer.valueOf(authorID)).get()) != null) {
//            author = authorRepository.findById(Integer.valueOf(authorID)).get();
//            System.out.println("ID EXISTS");
//            if (!authorName.isEmpty()) {
//                author.setAuthorName(authorName);
//            }
//            if(!authorDOB.toString().isEmpty()){
//                author.setAuthorDOB(authorDOB);
//            }
//        } else {
//            throw new IllegalStateException("something went terribly wrong");
//        }
//        return mappingDTOtoClass.mapToAuthor(author);
//    }

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


//        Map<String, String> hashMap = new HashMap<>();
//        System.out.println(Arrays.toString(authorDTO.getClass().getFields()));
//            for(Field f : authorDTO.getClass().getFields()){
//
//                if(f != null) {
//                    hashMap.put(f.getName(), String.valueOf(f));
//                    System.out.println(hashMap);
//                }
//            }

            return mappingDTOtoClass.mapToAuthor(author);
    }

    @Transactional
    public void deleteAuthor(String authorID){
        //check if author exists
        //check if author List<Book> is empty
        //if above is yes -> proceed with deleting author
        //if above is not -> delete the connection between book and author
        //delete author

        if(authorRepository.findById(Integer.valueOf(authorID)).isEmpty()) {

        }

    }

}
