package com.samcyber.quickstart.services;

import com.samcyber.quickstart.domain.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    public AuthorDto createUpdateAuthor(AuthorDto author);

    public List<AuthorDto> findAll();

    public Optional<AuthorDto> findAuthor(int id);

    public boolean authorExists(int id);

    public AuthorDto partialUpdate(AuthorDto author);

    void delete(int id);
}
