package com.samcyber.quickstart.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import java.util.Objects;

@Entity
@Table(name = "books")
//@JsonIgnoreProperties(ignoreUnknown = true)
// what this property would be doing is making it so any accepting JSON in the controller that looks to map to this
// object will ignore any unnecessary properties in the received JSON that it wasn't expecting.
public class Book {

    @Id
    private String isbn;
    @JsonProperty("Book Title")
    private String title;

//    changed this authorId to be of type Author instead of Integer, this is possible, I believe, because
//    now we are using JPA, the API understands java objects and their differences and handles conversion for us
//    when interacting with the database.
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author authorId;

    @JsonProperty("year")
//    What this is doing is changing the objectMapper mapping in jackson so that the output JSON will replace
//    this property name with 'year' instead of 'yearPublished'
    private String yearPublished;

    public Book(){
        this.isbn = null;
        this.title = null;
        this.authorId = null;
        this.yearPublished = null;
    }

    public Book(String isbn, String text, Author authorId, String yearPublished){
        this.isbn = isbn;
        this.title = text;
        this.authorId = authorId;
        this.yearPublished = yearPublished;
    }

    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public Author getAuthorId(){
        return authorId;
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

    public void setAuthorId(Author id){
        this.authorId = id;
    }
    public void setYearPublished(String year) {
        this.yearPublished = year;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(authorId, book.authorId) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + isbn.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + authorId.getId();
        return result;
    }

    @Override
    public String toString(){
        return "BookData{" +
                "isbn='" + isbn +
                "', text='" + title + "'" +
                ", authorId=" + authorId.getId() + ", yearPublished='" +
                yearPublished + "'}";
    }
}
