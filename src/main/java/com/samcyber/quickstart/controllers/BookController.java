package com.samcyber.quickstart.controllers;

import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookDto savedBookDto = bookService.createBook(isbn, bookDto);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
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
