package com.samcyber.quickstart.services;

import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookService {

    public BookDto createBook(String isbn, BookDto book);

    public List<BookDto> findAll();

    public Optional<BookDto> findBook(String isbn);
}
