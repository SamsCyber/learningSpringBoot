package com.samcyber.quickstart.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samcyber.quickstart.services.AuthorService;
import com.samcyber.quickstart.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class DtoEntityMapperIntegrationTests {

    private final BookService bookService;
    private final AuthorService authorService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public DtoEntityMapperIntegrationTests(MockMvc mockMvc,
                                           AuthorService authorService,
                                           BookService bookService)
    {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
        this.bookService = bookService;

    }

    @Test
    public void testThatMappingCorrectlyMapsAuthorJSONToAuthorDto(){}
}
