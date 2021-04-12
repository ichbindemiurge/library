package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping // api/book/bookid/paragraph/
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping
    public void addBook (@RequestBody Book book) {
        bookService.addBook(book);
    }

    @DeleteMapping(path = "{bookID}")
    public void deleteBook(@PathVariable("bookID") int bookID) {
        bookService.deleteBook(bookID);
    }

    @PutMapping(path = "{bookID}") //@RequestBody Book book
    public void updateBookFields(
            @RequestBody Book book) {

        // check what fields have been updated
        //check if they have been updated to something that is not null
        //update the fields

        // check bookName and bookAuthor not empty

        bookService.updateBookFields(book);
    }


    @RequestMapping(value = {"bookID"},method = RequestMethod.GET)
    public void searchBook(
            @RequestParam(required = true) int bookID){
        bookService.searchBook(bookID);
    }

    //serch books
}
