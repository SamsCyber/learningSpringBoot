package com.samcyber.quickstart.mappers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.samcyber.quickstart.domain.dto.AuthorDto;

import java.io.IOException;

public class authorDtoDeserializer extends JsonDeserializer<AuthorDto> {
    @Override
    public AuthorDto deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {

        JsonNode node = jsonParser.readValueAsTree();
        String name;
        Integer age;
        Integer id = (node.has("id")) ? node.get("id").asInt() : null;

        if(node.has("name")){
            name = node.get("name").asText();
        } else if(node.has("fullname")){
            name = node.get("fullname").asText();
        } else if(node.has("author")){
            name = node.get("author").asText();
        } else{
            name = null;
        }

        if(node.has("age")){
            age = node.get("age").asInt();
        } else {
            age = null;
        }

        return new AuthorDto(id, name, age);
    }
}
