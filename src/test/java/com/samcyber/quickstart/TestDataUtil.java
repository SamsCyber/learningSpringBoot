package com.samcyber.quickstart;

import com.samcyber.quickstart.domain.Author;
import com.samcyber.quickstart.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){}


    public static Author createTestAuthorA() {
        return new Author(1, "Jane Doe", 80);
    }

    public static Author createTestAuthorB() {
        return new Author(2, "John Doe", 50);
    }

    public static Author createTestAuthorC() {
        return new Author(3, "Peter Molyneux", 22);
    }

    public static Book createTestBookA(final Author author) {
        return new Book("987-654-321-0", "Big ol boys", author);
    }
    public static Book createTestBookB(final Author author) {
        return new Book("123-456-789-0", "Big buffoons", author);
    }
    public static Book createTestBookC(final Author author) {
        return new Book("999-666-333-1", "Big ol bear hug", author);
    }


}
