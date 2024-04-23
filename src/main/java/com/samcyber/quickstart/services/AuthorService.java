package com.samcyber.quickstart.services;

import com.samcyber.quickstart.domain.dto.AuthorDto;
import com.samcyber.quickstart.domain.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    public AuthorDto createAuthor(AuthorDto author);

    public List<AuthorDto> findAll();

    public Optional<AuthorDto> findAuthor(int id);
}
