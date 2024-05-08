package com.samcyber.quickstart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samcyber.quickstart.TestDataUtil;
import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.services.AuthorService;
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
public class AuthorControllerIntegrationTests {

    private final AuthorService authorService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorDto testAuthor = TestDataUtil.createTestAuthorDtoA();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatedAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorDto testAuthor = TestDataUtil.createTestAuthorDtoA();
        testAuthor.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor.getAge())
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception{
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA(), testAuthorB = TestDataUtil.createTestAuthorDtoB();
        authorService.createUpdateAuthor(testAuthorA);
        authorService.createUpdateAuthor(testAuthorB);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(testAuthorB.getAge())
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception{
        AuthorDto testAuthor = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/-1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetAuthorReturnsTheCorrectAuthor() throws Exception{
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        AuthorDto testAuthorB = TestDataUtil.createTestAuthorDtoB();
        authorService.createUpdateAuthor(testAuthorA);
        authorService.createUpdateAuthor(testAuthorB);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/2")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(2)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorB.getAge())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
        );
    }

    @Test
    public void testThatUpdateAuthorsUpdatesAndReturnsNewAuthor() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthorA);
        AuthorDto testAuthorB = TestDataUtil.createTestAuthorDtoB();
        String authorJson = objectMapper.writeValueAsString(testAuthorB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorB.getAge())
        );
    }

    @Test
    public void testThatUpdateAuthorsReturnsStatus200Ok() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthorA);
        AuthorDto testAuthorB = TestDataUtil.createTestAuthorDtoB();
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorB);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsStatus200Ok() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthorA);

        AuthorDto testAuthorB = TestDataUtil.createTestAuthorDtoB();
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorB);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthorA);

        AuthorDto testAuthorB = TestDataUtil.createTestAuthorDtoB();
        testAuthorB.setName("Radahn");
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorB);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Radahn")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorB.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsStatus204ForNonExistingAuthors() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/123456789")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorReturnsStatus204ForExistingAuthors() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorSuccessfullyRemovesAuthor() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        authorService.createUpdateAuthor(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
