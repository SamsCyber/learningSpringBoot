package com.samcyber.quickstart.repositories;

import com.samcyber.quickstart.TestDataUtil;
import com.samcyber.quickstart.domain.Author;
import com.samcyber.quickstart.domain.Book;
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

    private final BookRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void TestThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

//    @Test
//    public void TestThatMultipleBooksCanBeCreatedAndRecalled(){
//        Author authorA = TestDataUtil.createTestAuthorA();
//        Author authorB = TestDataUtil.createTestAuthorB();
//        authorRepo.create(authorA);
//        authorRepo.create(authorB);
//
//        Book bookA = TestDataUtil.createTestBookA();
//        Book bookB = TestDataUtil.createTestBookB();
//        Book bookC = TestDataUtil.createTestBookC();
//        bookA.setAuthorId(authorA.getId());
//        bookB.setAuthorId(authorB.getId());
//        bookC.setAuthorId(authorB.getId());
//        underTest.create(bookA);
//        underTest.create(bookB);
//        underTest.create(bookC);
//
//        List<Book> result = underTest.find();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(bookA, bookB, bookC);
//    }

//    @Test
//    public void TestThatBooksCanBeUpdated(){
//        Author author = TestDataUtil.createTestAuthorA();
//        authorRepo.create(author);
//
//        Book book = TestDataUtil.createTestBookA();
//        book.setAuthorId(author.getId());
//        underTest.create(book);
//
//        book.setIsbn("TestingUpdated");
//        underTest.update("987-654-321-0", book);
//
//        Optional<Book> result = underTest.findOne("TestingUpdated");
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(book);
//    }
//
//    @Test
//    public void TestThatBooksCanBeDeleted(){
//        Author author = TestDataUtil.createTestAuthorA();
//        authorRepo.create(author);
//
//        Book book = TestDataUtil.createTestBookA();
//        book.setAuthorId(author.getId());
//        underTest.create(book);
//
//        underTest.delete(book.getIsbn());
//
//        Optional<Book> result = underTest.findOne(book.getIsbn());
//        assertThat(result).isEmpty();
//    }
}
