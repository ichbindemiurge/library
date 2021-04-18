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

    @PutMapping(value = "{authorID}")
    public AuthorDTO updateAuthor(
                                  @PathVariable("authorID") String authorID,
                                  @RequestParam(required = false) String authorName,
                                  @RequestParam(required = false) LocalDate authorDOB) {

        return authorService.updateAuthor(authorID, authorName, authorDOB);
    }

}
