package com.samcyber.quickstart.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "books")
//@JsonIgnoreProperties(ignoreUnknown = true)
// what this property would be doing is making it so any accepting JSON in the controller that looks to map to this
// object will ignore any unnecessary properties in the received JSON that it wasn't expecting.
public class BookEntity {

    @Id
    private String isbn;
    @JsonProperty("Book Title")
    private String title;

//    changed this authorId to be of type Author instead of Integer, this is possible, I believe, because
//    now we are using JPA, the API understands java objects and their differences and handles conversion for us
//    when interacting with the database.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

    @JsonProperty("year")
//    What this is doing is changing the objectMapper mapping in jackson so that the output JSON will replace
//    this property name with 'year' instead of 'yearPublished'
    private String yearPublished;

    public BookEntity(){
        this.isbn = null;
        this.title = null;
        this.authorEntity = null;
        this.yearPublished = null;
    }

    public BookEntity(String isbn, String text, AuthorEntity authorEntity, String yearPublished){
        this.isbn = isbn;
        this.title = text;
        this.authorEntity = authorEntity;
        this.yearPublished = yearPublished;
    }

    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public AuthorEntity getAuthorId(){
        return authorEntity;
    }
    public String getYearPublished() {
        return yearPublished;
    }
    public void setTitle(String text){
        this.title = text;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setAuthor(AuthorEntity author){
        this.authorEntity = author;
    }
    public void setYearPublished(String year) {
        this.yearPublished = year;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BookEntity bookEntity = (BookEntity) o;
        return Objects.equals(isbn, bookEntity.isbn) && Objects.equals(authorEntity, bookEntity.authorEntity) && Objects.equals(title, bookEntity.title);
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + isbn.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + authorEntity.getId();
        return result;
    }

    @Override
    public String toString(){
        return "BookData{" +
                "isbn='" + isbn +
                "', text='" + title + "'" +
                ", authorId=" + authorEntity.getId() + ", yearPublished='" +
                yearPublished + "'}";
    }
}
