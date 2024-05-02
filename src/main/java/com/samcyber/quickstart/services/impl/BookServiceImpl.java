package com.samcyber.quickstart.services.impl;

import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.BookEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.repositories.BookRepository;
import com.samcyber.quickstart.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final Mapper<BookEntity, BookDto> mapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(Mapper<BookEntity, BookDto> mapper, BookRepository bookRepository){
        this.mapper = mapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createOrUpdateBook(String isbn, BookDto book) {
        BookEntity mappedEntity = mapper.mapFrom(book);
        mappedEntity.setIsbn(isbn);
        mappedEntity = bookRepository.save(mappedEntity);
        return mapper.mapTo(mappedEntity);
    }

    @Override
    public List<BookDto> findAll(){
        List<BookEntity> allBooks = StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
        return allBooks.stream().map(mapper::mapTo).toList();
    }

    @Override
    public Optional<BookDto> findBook(String isbn) {
        Optional<BookEntity> bookIfExists = bookRepository.findById(isbn);
        return bookIfExists.map(mapper::mapTo);
    }

    @Override
    public boolean bookExists(String isbn) {
        return bookRepository.existsById(isbn);
    }
}
