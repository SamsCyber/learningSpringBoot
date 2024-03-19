package com.samcyber.quickstart.dao.impl;

import com.samcyber.quickstart.dao.BookDao;
import com.samcyber.quickstart.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;

public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book){
        jdbcTemplate.update(
                "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)", book.getIsbn(),
                book.getTitle(),
                book.getAuthorId()
        );
    }
}
