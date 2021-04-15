package com.example.demo.controller;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.service.AuthorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public void addAuthor (@RequestBody AuthorDTO authorDTO) {
        authorService.addAuthor(authorDTO);
    }


}
