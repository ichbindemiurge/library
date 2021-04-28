package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.mapping.MappingDTOtoClass;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private MappingDTOtoClass mappingDTOtoClaas = new MappingDTOtoClass();



    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    public List<BookDTO> getBooks() {
        List<Book> allBooks = bookRepository.findAll();
        List<BookDTO> bookDTOS = new ArrayList<>(allBooks.size());

        for (Book book : allBooks) {
            BookDTO bookDTO = mappingDTOtoClaas.bookToDTO(book);
            bookDTO.setAuthorsIDsDTOList(getAuthorsIDsFromBook(book));
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Transactional
    public void addBook(BookDTO bookDTO) {
        List<Author> authorsToAdd = getAuthorsFromDTO(bookDTO);
        Book bookToSave = mappingDTOtoClaas.DTOToBook(bookDTO);
        linkAuthor(authorsToAdd, bookToSave);
        bookToSave.setAuthorsList(authorsToAdd);
        bookRepository.save(bookToSave);
    }

    @Transactional
    public void deleteBook(int bookID) {
        Book book = bookRepository.findById(bookID).orElseThrow(
                () -> new IllegalStateException("This book does not exist in the DB")
        );
        if(!book.getAuthorsList().isEmpty()){
            removeAuthorFromBook(book.getAuthorsList(), book);
        }
        bookRepository.deleteById(bookID);
    }

    @Transactional
    public BookDTO searchBook(String bookName) {
            Book book = bookRepository.findBookByBookName(bookName).orElseThrow(
                    () -> new IllegalStateException("This book does not exist in the DB")
            );
            BookDTO bookDTO = mappingDTOtoClaas.bookToDTO(book);
            bookDTO.setAuthorsIDsDTOList(getAuthorsIDsFromBook(book));
            return bookDTO;
    }

    @Transactional
    public BookDTO updateBookFields(BookDTO bookDTO) {
        Book book = bookRepository.findById(bookDTO.getId()).orElseThrow(
                () -> new IllegalStateException("This book does not exist in the DB")
        );

        //bookName update
        if (!bookDTO.getBookName().isEmpty()) {
            book.setBookName(bookDTO.getBookName());
        }

        //book authors update
        if (!bookDTO.getAuthorsIDsDTOList().isEmpty()) {
            List<Author> authorsToAdd = getAuthorsFromDTO(bookDTO);
            for (Author author : authorsToAdd) {
                List<Author> existingAuthors = book.getAuthorsList();
                if (!existingAuthors.contains(author)) {
                    existingAuthors.add(author);
                    author.addBook(book);
                }
            }
        }

        //book description update
        String bookDescription = bookDTO.getBookDescription();
        if (bookDescription != null) {
            book.setBookDescription(bookDescription.trim());
        }
        return mappingDTOtoClaas.bookToDTO(book);
    }


    public void linkAuthor(List<Author> authorList, Book book) {
        authorList.forEach(author -> author.addBook(book));
    }

    public void removeAuthorFromBook(List<Author> authorList, Book book){
        authorList.forEach(author -> author.removeBook(book));
    }

    public List<Author> getAuthorsFromDTO(BookDTO bookDTO) {
        List<String> authorIDs = bookDTO.getAuthorsIDsDTOList();
        List<Author> authors = new ArrayList<>(authorIDs.size());
        for (String authorID : authorIDs) {
            authorRepository.findById(Integer.valueOf(authorID)).ifPresent(authors::add);
        }
        return authors;
    }

    public List<String> getAuthorsIDsFromBook(Book book) {
        List<Author> authors = book.getAuthorsList();
        List<String> authorIDs = new ArrayList<>(authors.size());

        for (Author author : authors) {
            authorRepository.findById(author.getId()).ifPresent( a -> authorIDs.add(String.valueOf(a.getId())));
        }
        return authorIDs;
    }



}
