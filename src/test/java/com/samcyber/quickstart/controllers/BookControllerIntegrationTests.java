package com.samcyber.quickstart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samcyber.quickstart.TestDataUtil;
import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;
import com.samcyber.quickstart.services.BookService;
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

    private final BookService bookService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
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

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception{
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);

        bookService.createOrUpdateBook(testBookA.getIsbn(), testBookA);
        bookService.createOrUpdateBook(testBookB.getIsbn(), testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].isbn").value(testBookB.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].title").value(testBookB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].author").value(testBookB.getAuthor())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].yearPublished").value(testBookB.getYearPublished())
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200WhenBookExists() throws Exception{
        BookDto testBook = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetBookReturnsHttpStatus404WhenBookDoesNotExist() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/-1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void TestThatGetBookReturnsTheCorrectBookIfItExists() throws Exception{
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(testAuthorDto);

        String jsonTestAuthorDto = objectMapper.writeValueAsString(testAuthorDto);
        String jsonTestBookDto = objectMapper.writeValueAsString(testBookDto);

//        Use this variable instead to simulate a user inserting a book via isbn URI different to the one contained in
//        their bookDto object
        String isbnInUrl = "123-changeIsbn-678";
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + isbnInUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTestBookDto)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + isbnInUrl)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(isbnInUrl)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.name").value(testAuthorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author.age").value(testAuthorDto.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearPublished").value(testBookDto.getYearPublished())
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook("12345", testBookA);

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);
        String testBookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateBookUpdatesAndReturnsCorrectly() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook(testBookA.getIsbn(), testBookA);

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);
        String testBookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearPublished").value(testBookB.getYearPublished())
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200Ok() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook(testBookA.getIsbn(), testBookA);

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);
        testBookB.setYearPublished(null);
        String testBookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookWorksAndReturnsCorrectly() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook(testBookA.getIsbn(), testBookA);

        BookDto testBookB = TestDataUtil.createTestBookDtoB(null);
        testBookB.setYearPublished(null);
        String testBookJson = objectMapper.writeValueAsString(testBookB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearPublished").value(testBookA.getYearPublished())
        );
    }

    @Test
    public void testThatDeleteBookReturnsStatusCode204IfDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/123456789")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorReturnsStatus204ForExistingAuthors() throws Exception {
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook(testBookDto.getIsbn(), testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorSuccessfullyRemovesAuthor() throws Exception {
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        bookService.createOrUpdateBook(testBookDto.getIsbn(), testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearPublished").value(testBookDto.getYearPublished())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

}
