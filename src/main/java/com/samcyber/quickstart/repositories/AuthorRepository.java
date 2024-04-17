package com.samcyber.quickstart.repositories;

import com.samcyber.quickstart.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

//    JPA is clever enough to recognise that age is a parameter of Author, LessThan is a common comparison, figures out
//    that parameter is going to be referring to the value of age to compare to. Hence, we don't need to provide implementation
    Iterable<Author> ageLessThan(int age);

//    Another simple method, if the naming doesn't quite fit into the convention it looks for, can instead write
//    HQL (Hibernate query language) which isn't quite SQL, it is more OO based.
    @Query("SELECT a from Author a where a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int i);
}
