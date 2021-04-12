package com.example.demo.service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void addBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findBookByBookName(book.getBookName());

        if(bookOptional.isPresent()){
            throw new IllegalStateException("NAME EXISTS");
        } else {
            bookRepository.save(book);
        }
        System.out.println(book);
    }

    @Transactional
    public void deleteBook(int bookID) {
        if(!bookRepository.existsById(bookID)) {
            throw new IllegalStateException("NO SUCH BOOK");
        } else {
            bookRepository.deleteById(bookID);
        }
    }

    @Transactional
    public void searchBook(int bookID) {
        System.out.println("BLBLLDSALDASDASDLADLA");
            bookRepository.findById(bookID);
    }

    @Transactional
    public void updateBookFields(Book book) {


            book.setBookName(book.getBookName());

//        Book book = bookRepository.findById(bookID).orElseThrow(()-> new IllegalStateException("something went terribly wrong"));
//
//        //bookName.isBlank() && bookAuthor
//        // instead of params -> Book. do a validation for object
//
//        if (bookName != null && bookName.length() > 0 && !Objects.equals(book.getBookName(), bookName)) {
//            book.setBookName(bookName);
//        }
//
//        if (bookAuthor != null && bookAuthor.size() > 0 && !Objects.equals(book.getBooksAuthor(), bookAuthor)) {
//            book.setBooksAuthor(bookAuthor);
//        }
    }

//    public List<Book> getBooks() {
//        return List.of(
//                new Book(
//                        0,
//                        "TestBookName",
//                        "This book is about something",
//                        Collections.singletonList("Author 1" + "Author 2" + "Author 3")
//                )
//        );
//    }
}
