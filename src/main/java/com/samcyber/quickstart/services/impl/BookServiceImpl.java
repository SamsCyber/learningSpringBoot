package com.samcyber.quickstart.services.impl;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.domain.entities.BookEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.repositories.AuthorRepository;
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
    private final AuthorRepository authorRepository;

    public BookServiceImpl(Mapper<BookEntity, BookDto> mapper, BookRepository bookRepository, AuthorRepository authorRepository){
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDto createOrUpdateBook(String isbn, BookDto book) {
        AuthorDto author = book.getAuthor();
        BookEntity mappedEntity = mapper.mapFrom(book);
        mappedEntity.setIsbn(isbn);
        if(author != null && author.getId() != null){
            Optional<AuthorEntity> authorExists = authorRepository.findById(author.getId());
            authorExists.ifPresent(mappedEntity::setAuthor);
        }
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

    @Override
    public BookDto partialUpdate(String isbn, BookDto bookDto){
        bookDto.setIsbn(isbn);
        AuthorDto author = bookDto.getAuthor();
        BookEntity bookEntity = mapper.mapFrom(bookDto);
        if(author != null && author.getId() != null){
            Optional<AuthorEntity> authorExists = authorRepository.findById(author.getId());
            authorExists.ifPresent(bookEntity::setAuthor);
        }
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            Optional.ofNullable(bookEntity.getAuthor()).ifPresent(existingBook::setAuthor);
            Optional.ofNullable(bookEntity.getYearPublished()).ifPresent(existingBook::setYearPublished);
            return mapper.mapTo(bookRepository.save(existingBook));
        }).orElseThrow(() -> new RuntimeException("No book exists with that isbn"));
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
