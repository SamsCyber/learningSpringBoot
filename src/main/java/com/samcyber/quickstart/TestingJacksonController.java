package com.samcyber.quickstart;

import com.samcyber.quickstart.domain.Author;
import com.samcyber.quickstart.domain.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
public class TestingJacksonController {

    private static final Logger LOGGER = Logger.getLogger(TestingJacksonController.class.getName());

    @GetMapping(path = "/books")
    public Book retrieveBook(){
        Author moloN = new Author(3, "Peter Molyneux", 22);
        return new Book("testingBigTime", "OhWhereDidTheTimeGo", moloN, "2003");
    }

    @PostMapping(path = "/books")
    public Book sendBook(@RequestBody final Book book){
        LOGGER.info("Got book: " + book.toString());
        return book;
    }
} 
