package com.samcyber.quickstart.controllers;

import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        boolean alreadyExists = bookService.bookExists(isbn);
        BookDto createdOrUpdatedBookDto = bookService.createOrUpdateBook(isbn, bookDto);
        if(!alreadyExists){
            return new ResponseEntity<>(createdOrUpdatedBookDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(createdOrUpdatedBookDto, HttpStatus.OK);
        }
    }

    @GetMapping("/books")
    public List<BookDto> listBooks() {
        return bookService.findAll();
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> findBook(@PathVariable("isbn") String isbn){
        Optional<BookDto> bookIfExists = bookService.findBook(isbn);
        return bookIfExists.map(
                bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
