package com.samcyber.quickstart.mappers.impl;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.dto.BookDto;
import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.domain.entities.BookEntity;
import com.samcyber.quickstart.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private final ModelMapper mapper;

    public BookMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return mapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return mapper.map(bookDto, BookEntity.class);
    }
}
