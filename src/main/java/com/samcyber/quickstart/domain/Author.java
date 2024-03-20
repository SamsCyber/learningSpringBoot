package com.samcyber.quickstart.domain;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class Author {

    private Integer id;
    private String name;
    private Integer age;

    public Author(){
        this.id = null;
        this.name = null;
        this.age = null;
    }

    public Author(int id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
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
        return "AuthorData{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", age=" + age + '}';
    }
}
