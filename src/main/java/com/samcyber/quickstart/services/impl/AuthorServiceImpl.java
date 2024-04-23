package com.samcyber.quickstart.services.impl;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.mappers.Mapper;
import com.samcyber.quickstart.repositories.AuthorRepository;
import com.samcyber.quickstart.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final Mapper<AuthorEntity, AuthorDto> authorMapper;
    private final AuthorRepository authorRepository;

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

    @Override
    public List<AuthorDto> findAll(){
        List<AuthorEntity> dbResult = StreamSupport.stream(authorRepository.findAll().spliterator(), false).toList();
        return dbResult.stream().map(authorMapper::mapTo).toList();
    }

    @Override
    public Optional<AuthorDto> findAuthor(int id){
        Optional<AuthorEntity> authorIfExists = authorRepository.findById(id);
        return authorIfExists.map(authorMapper::mapTo);
    }
}
