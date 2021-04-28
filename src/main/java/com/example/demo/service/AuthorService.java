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
        mappingDTOtoClass.DTOToAuthor(authorRepository.findAuthorByAuthorName(authorName).get());
    }


    @Transactional
    public Author getAuthorById(String authorID){
        return authorRepository.findById(Integer.valueOf(authorID)).get();
    }

//    @Transactional
//    public AuthorDTO updateAuthor(String authorID, String authorName, LocalDate authorDOB) {
//        Author author;
//        if (mappingDTOtoClass.DTOToAuthor(authorRepository.findById(Integer.valueOf(authorID)).get()) != null) {
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
//        return mappingDTOtoClass.DTOToAuthor(author);
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
            return mappingDTOtoClass.DTOToAuthor(author);
    }

    @Transactional
    public void deleteAuthor(String authorID){
        //check if author exists
        //check if author List<Book> is empty
        //if above is yes -> proceed with deleting author

        //if above is no -> check if this is the only author for the book

        // if above is yes -> delete author AND book
        // if no -> delete author from the AuthorList of that book
        //delete author

        Author author = authorRepository.findById(Integer.valueOf(authorID)).orElseThrow(
                () -> new IllegalStateException("This author does not exist in the DB")
        );
        List<Book> linkedBooks = author.getBooksList(); // ALL books assigned to THAT author
        List<Book> booksToDelete = new ArrayList<>();



        if(!linkedBooks.isEmpty()) {
            //check if this is the only author for the book
            for (Book book : linkedBooks) { // if while going through ALL BOOKS of THAT author

                List<Author> oneBookAuthors = book.getAuthorsList(); //  ONE particular book's authors
                //has ONLY THAT author
                if(oneBookAuthors.contains(author) && oneBookAuthors.size() == 1){
                    booksToDelete.add(book);
                    //book.removeAuthor(author) try this next time without separate if()

                } //else {
//                    //author.removeBook(book);
//                }
            }

            if (!booksToDelete.isEmpty()){
                booksToDelete.forEach(book -> book.removeAuthor(author));
            }
        }
        authorRepository.deleteById(Integer.valueOf(authorID));

    }

    public void removeAuthorFromBook(Author author, Book books){
        books.removeAuthor(author);
    }


}
