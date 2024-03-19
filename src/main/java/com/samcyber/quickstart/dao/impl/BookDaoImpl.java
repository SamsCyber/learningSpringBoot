package com.samcyber.quickstart.dao.impl;

import com.samcyber.quickstart.dao.BookDao;
import com.samcyber.quickstart.domain.Author;
import com.samcyber.quickstart.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
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

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> results = jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(), isbn
        );
        return results.stream().findFirst();
    }

    public List<Book> find() {
        return jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books",
                new BookRowMapper()
        );
    }

    public static class BookRowMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getString("isbn"), rs.getString("title"),
                    rs.getInt("author_id"));
        }
    }
}
