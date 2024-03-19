package com.samcyber.quickstart.dao;

import com.samcyber.quickstart.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(int i);

    List<Author> find();
}
