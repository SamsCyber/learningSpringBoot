package com.samcyber.quickstart.controllers;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        return new ResponseEntity<>(authorService.createUpdateAuthor(author), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors() {
        return authorService.findAll();
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") int id) {
        Optional<AuthorDto> authorIfExists = authorService.findAuthor(id);
        return authorIfExists.map(authorDto -> new ResponseEntity<>(authorDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> authorFullUpdate(@PathVariable("id") int id, @RequestBody AuthorDto author){
        if(!authorService.authorExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        author.setId(id);
        AuthorDto savedDto = authorService.createUpdateAuthor(author);
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> authorPartialUpdate(@PathVariable("id") int id, @RequestBody AuthorDto author){
        if(!authorService.authorExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        author.setId(id);
        AuthorDto updatedAuthor = authorService.partialUpdate(author);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") int id){
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
