package com.samcyber.quickstart.mappers.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.dto.BookDto;

import java.io.IOException;

public class bookDtoDeserializer extends JsonDeserializer<BookDto> {

    @Override
    public BookDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jsonParser.readValueAsTree();
        String isbn = (node.has("isbn")) ? node.get("isbn").asText() : null;
        String title;
        AuthorDto author;
        Integer yearPublished;

        if(node.has("title")){
            title = node.get("title").asText();
        } else if(node.has("book title")) {
            title = node.get("book title").asText();
        } else if(node.has("book name")) {
            title = node.get("book name").asText();
        } else {
            title = null;
        }

        if(node.has("year")){
            yearPublished = node.get("year").asInt();
        } else if(node.has("yearPublished")) {
            yearPublished = node.get("yearPublished").asInt();
        } else if(node.has("published")) {
            yearPublished = node.get("published").asInt();
        } else {
            yearPublished = null;
        }

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode authorNode = node.get("author");
        if(authorNode != null){
            author = mapper.treeToValue(authorNode, AuthorDto.class);
        } else{
            author = null;
        }

        return new BookDto(isbn, title, author, yearPublished);
    }
}
