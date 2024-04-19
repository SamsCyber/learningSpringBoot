package com.samcyber.quickstart.repositories;

import com.samcyber.quickstart.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
}
