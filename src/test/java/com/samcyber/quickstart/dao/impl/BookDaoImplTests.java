package com.samcyber.quickstart.dao.impl;

import com.samcyber.quickstart.dao.TestDataUtil;
import com.samcyber.quickstart.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {


    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);
        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq("987-654-321-0"), eq("Big ol boys"), eq(1)
        );
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql(){
        underTest.findOne("987-654-321-0");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(), eq("987-654-321-0")
        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();
        underTest.update("420-1337", book);
        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq("987-654-321-0"), eq("Big ol boys"), eq(1), eq("420-1337")
        );
    }
}
