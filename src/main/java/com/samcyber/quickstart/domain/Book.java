package com.samcyber.quickstart.domain;

import org.springframework.lang.NonNull;
import java.util.Objects;

public class Book {
    private String isbn;
    private String title;
    private Integer authorId;

    public Book(){
        this.isbn = null;
        this.title = null;
        this.authorId = null;
    }

    public Book(String isbn, String text, int authorId){
        this.isbn = isbn;
        this.title = text;
        this.authorId = authorId;
    }

    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public int getAuthorId(){
        return authorId;
    }
    public void setTitle(String text){
        this.title = text;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setAuthorId(int id){
        this.authorId = id;
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
        result = 31 * result + authorId;
        return result;
    }

    @Override
    public String toString(){
        return "BookData{" +
                "isbn='" + isbn +
                "', text='" + title + "'" +
                ", authorId=" + authorId + '}';
    }
}
