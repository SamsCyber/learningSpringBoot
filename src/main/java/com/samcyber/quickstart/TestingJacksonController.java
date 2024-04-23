package com.samcyber.quickstart;

import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.domain.entities.BookEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

//@RestController
//public class TestingJacksonController {
//
//    private static final Logger LOGGER = Logger.getLogger(TestingJacksonController.class.getName());
//
//    @GetMapping(path = "/books")
//    public BookEntity retrieveBook(){
//        AuthorEntity moloN = new AuthorEntity(3, "Peter Molyneux", 22);
//        return new BookEntity("testingBigTime", "OhWhereDidTheTimeGo", moloN, "2003");
//    }
//
//    @PostMapping(path = "/books")
//    public BookEntity sendBook(@RequestBody final BookEntity bookEntity){
//        LOGGER.info("Got book: " + bookEntity.toString());
//        return bookEntity;
//    }
//}
