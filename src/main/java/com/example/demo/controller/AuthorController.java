package com.example.demo.controller;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public void addAuthor (@RequestBody AuthorDTO authorDTO) {
        authorService.addAuthor(authorDTO);
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors () {
        return authorService.getAllAuthors();
    }

    @GetMapping(path = "{authorName}")
    public void getAuthor(@PathVariable("authorName") String authorName) {
        authorService.getAuthor(authorName);
    }

    @PutMapping(value = "/{authorID}")
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO,
                             @PathVariable("authorID") int authorID) {


        return authorService.updateAuthor(authorDTO);
    }

    //@PathVariable("bookID") int bookID,
    //            @RequestParam(required = false) String bookName,
    //            @RequestParam(required = false) List<Book> bookAuthor) {


}
