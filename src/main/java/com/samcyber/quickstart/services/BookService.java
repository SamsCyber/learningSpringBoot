package com.samcyber.quickstart.services;

import com.samcyber.quickstart.domain.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    public BookDto createOrUpdateBook(String isbn, BookDto book);

    public List<BookDto> findAll();

    public Optional<BookDto> findBook(String isbn);

    public boolean bookExists(String isbn);

    BookDto partialUpdate(String isbn, BookDto bookDto);

    void deleteBook(String isbn);
}
