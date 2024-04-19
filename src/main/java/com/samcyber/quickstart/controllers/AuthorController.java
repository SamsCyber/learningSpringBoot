package com.samcyber.quickstart.controllers;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.services.AuthorService;
import com.samcyber.quickstart.services.impl.AuthorServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        return new ResponseEntity<>(authorService.createAuthor(author), HttpStatus.CREATED);
    }
}
