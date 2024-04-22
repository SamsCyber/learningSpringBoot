package com.samcyber.quickstart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samcyber.quickstart.TestDataUtil;
import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatIsbnIsOverwrittenFromUserByUrlIsbn() throws Exception {
        BookEntity testBook = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(testBook);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/999-666-333")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("999-666-333")
        );
    }

    @Test
    public void testThatCreatedBookSuccessfullyReturnsHttp201Created() throws Exception{
        BookDto testBook = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBook);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/123-456-789")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreateBook() throws Exception {
        BookDto testBook = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBook);
//        System.out.println(bookJson);
        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + testBook.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(testBook.getAuthor())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearPublished").value(testBook.getYearPublished())
        );
    }
}
