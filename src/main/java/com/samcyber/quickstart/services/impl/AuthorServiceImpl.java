package com.samcyber.quickstart.services.impl;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.repositories.AuthorRepository;
import com.samcyber.quickstart.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private Mapper<AuthorEntity, AuthorDto> authorMapper;
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(Mapper<AuthorEntity, AuthorDto> authorMapper, AuthorRepository authorRepository){
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDto createAuthor(AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedEntity = authorRepository.save(authorEntity);
        return authorMapper.mapTo(savedEntity);
    }
}
