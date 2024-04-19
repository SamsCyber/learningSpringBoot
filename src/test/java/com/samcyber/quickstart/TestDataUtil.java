package com.samcyber.quickstart;

import com.samcyber.quickstart.domain.entities.AuthorEntity;
import com.samcyber.quickstart.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil(){}


    public static AuthorEntity createTestAuthorA() {
        return new AuthorEntity(1, "Jane Doe", 80);
    }

    public static AuthorEntity createTestAuthorB() {
        return new AuthorEntity(2, "John Doe", 49);
    }

    public static AuthorEntity createTestAuthorC() {
        return new AuthorEntity(3, "Peter Molyneux", 22);
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return new BookEntity("987-654-321-0", "Big ol boys", authorEntity, "1999");
    }
    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return new BookEntity("123-456-789-0", "Big buffoons", authorEntity, "1870");
    }
    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return new BookEntity("999-666-333-1", "Big ol bear hug", authorEntity, "10");
    }


}
