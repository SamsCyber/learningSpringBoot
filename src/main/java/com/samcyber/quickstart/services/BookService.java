package com.samcyber.quickstart.services;

import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;

public interface BookService {

    public BookDto createBook(String isbn, BookDto book);
}
