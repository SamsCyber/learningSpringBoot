package com.samcyber.quickstart.domain.dto;

import java.util.Objects;

public class BookDto {



    private String isbn;

    private String title;

    private AuthorDto author;

    private Integer yearPublished;

    public BookDto(){
        this.isbn = null;
        this.title = null;
        this.author = null;
        this.yearPublished = null;
    }

    public BookDto(String isbn, String title, AuthorDto authorDto, Integer yearPublished){
        this.isbn = isbn;
        this.title = title;
        this.author = authorDto;
        this.yearPublished = yearPublished;
    }

    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public AuthorDto getAuthor(){
        return author;
    }
    public Integer getYearPublished() {
        return yearPublished;
    }
    public void setTitle(String text){
        this.title = text;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setAuthor(AuthorDto author){
        this.author = author;
    }
    public void setYearPublished(Integer year) {
        this.yearPublished = year;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BookDto bookEntity = (BookDto) o;
        return Objects.equals(isbn, bookEntity.isbn) && Objects.equals(author, bookEntity.author) && Objects.equals(title, bookEntity.title);
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + isbn.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + author.getId();
        return result;
    }

    @Override
    public String toString(){
        return "BookData{" +
                "isbn='" + isbn +
                "', text='" + title + "'" +
                ", author=" + author.toString() + ", yearPublished='" +
                yearPublished + "'}";
    }


}
