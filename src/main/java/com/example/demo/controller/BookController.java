package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<BookDTO> getBooks() {
        return bookService.getBooks();
    }


    @PostMapping
    public void addBook (@RequestBody BookDTO bookDTO) {
        if(bookDTO.getAuthorsIDsDTOList().isEmpty() || bookDTO.getBookName().isEmpty()){
            throw new IllegalStateException("The name or authors field is empty. Please check the data before proceeding");
        } else {
            bookService.addBook(bookDTO);
        }
    }


    @DeleteMapping(path = "{bookID}")
    public void deleteBook(@PathVariable("bookID") int bookID) {
        bookService.deleteBook(bookID);
    }

    @PutMapping
    public BookDTO updateBookFields(@RequestBody BookDTO bookDTO) {
        if(!bookDTO.getBookName().isEmpty()) {
            return bookService.updateBookFields(bookDTO);
        } else {
            throw new IllegalStateException("The name of the book is EMPTY");
        }
    }


    @GetMapping(path = "{bookName}")
    public BookDTO searchBook(@PathVariable("bookName") String bookName){
        return bookService.searchBook(bookName);
    }

}
