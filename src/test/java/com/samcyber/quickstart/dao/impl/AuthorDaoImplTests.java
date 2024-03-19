package com.samcyber.quickstart.dao.impl;

import com.samcyber.quickstart.dao.impl.AuthorDaoImpl;
import com.samcyber.quickstart.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql(){
        Author author = new Author(1, "Jane Doe", 80);
        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1), eq("Jane Doe"), eq(80)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql(){
        underTest.findOne(1);

        verify(jdbcTemplate).query(
                "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                any(RowMapper.class), eq(1)
        );
    }
}
