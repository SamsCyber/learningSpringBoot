package com.samcyber.quickstart.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    private Integer id;
    private String name;
    private Integer age;

    public AuthorEntity(){
        this.id = null;
        this.name = null;
        this.age = null;
    }

    public AuthorEntity(int id, String name, int age){
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
    public void setAge(int age){
        this.age = age;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        AuthorEntity authorEntity = (AuthorEntity) o;
        return Objects.equals(id, authorEntity.id) && Objects.equals(age, authorEntity.age) && Objects.equals(name, authorEntity.name);
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
                "name='" + name + "'" +
                ", age=" + age + '}';
    }
}
