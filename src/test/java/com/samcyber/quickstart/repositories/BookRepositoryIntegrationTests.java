package com.samcyber.quickstart.repositories;

import com.samcyber.quickstart.TestDataUtil;
import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void TestThatBookCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void TestThatMultipleBooksCanBeCreatedAndRecalled(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntityA);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntityB);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntityB);

        underTest.save(bookEntityA);
        underTest.save(bookEntityB);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void TestThatBooksCanBeUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);

        bookEntity.setIsbn("TestingUpdated");
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById("TestingUpdated");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void TestThatBooksCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> resultInitial = underTest.findById(bookEntity.getIsbn());
        assertThat(resultInitial).isPresent();

        underTest.deleteById(bookEntity.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();
    }
}
