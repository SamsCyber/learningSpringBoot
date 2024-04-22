package com.samcyber.quickstart.services.impl;

import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.repositories.BookRepository;
import com.samcyber.quickstart.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final Mapper<BookEntity, BookDto> mapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(Mapper<BookEntity, BookDto> mapper, BookRepository bookRepository){
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(String isbn, BookDto book) {
        BookEntity mappedEntity = mapper.mapFrom(book);
        mappedEntity.setIsbn(isbn);
        mappedEntity = bookRepository.save(mappedEntity);
        return mapper.mapTo(mappedEntity);
    }
}
