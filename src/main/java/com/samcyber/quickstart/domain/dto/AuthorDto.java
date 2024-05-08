package com.samcyber.quickstart.domain.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.samcyber.quickstart.mappers.deserializers.authorDtoDeserializer;

import java.util.Objects;

@JsonDeserialize(using = authorDtoDeserializer.class)
public class AuthorDto {

    private Integer id;
    private String name;
    private Integer age;

    public AuthorDto(){
        this.id = null;
        this.name = null;
        this.age = null;
    }

    public AuthorDto(Integer id, String name, Integer age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }
    public Integer getAge(){
        return age;
    }
    public Integer getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(Integer age){
        this.age = age;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AuthorDto author = (AuthorDto) o;
        return Objects.equals(id, author.id) && Objects.equals(age, author.age) && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString(){
        return "{" +
                "name='" + name + "'" +
                ", age=" + age + '}';
    }
}
