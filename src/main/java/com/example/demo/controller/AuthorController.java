package com.example.demo.controller;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.dto.BookDTO;
import com.example.demo.service.AuthorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
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


    @PutMapping
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthor(authorDTO);
    }

    @DeleteMapping(value = "{authorID}")
    public void deleteAuthor(@PathVariable("authorID") String authorID) {
        authorService.deleteAuthor(authorID);
    }

}
