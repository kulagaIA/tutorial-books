package com.tutorial.books.repository.impl;

import com.tutorial.books.repository.TutorialBooksRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("junit")
public class UserJdbcRepositoryTest extends TutorialBooksRepositoryTest {

    @Autowired
    private UserJdbcRepositoryImpl repository;

    @BeforeEach
    void clearUsers() {
        clearTables();
    }

    @Test
    void testGetAll() {
        var user1 = createUser();
        var user2 = createUser();
        var user3 = createUser();

        var result = repository.getAll();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(user1);
        assertThat(result).contains(user2);
        assertThat(result).contains(user3);
    }

    @Test
    void testGetById() {
        createUser();
        createUser();
        var user = createUser();

        var result = repository.getById(user.getId());

        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    void testGetByBookId() {
        createUser();
        var user1 = createUser();
        var user2 = createUser();
        createUser();

        createBook();
        var book1 = createBook();
        var book2 = createBook();

        giveBookToUser(book1, user1);
        giveBookToUser(book2, user1);
        giveBookToUser(book1, user2);

        var result = repository.getByBookId(book1.getId());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(user1);
        assertThat(result).contains(user2);
    }

    @Test
    void testGetByBookIdWhenOnlyOneUserHasBook() {
        createUser();
        var user1 = createUser();
        var user2 = createUser();
        createUser();

        createBook();
        var book1 = createBook();
        var book2 = createBook();

        giveBookToUser(book1, user1);
        giveBookToUser(book2, user1);
        giveBookToUser(book1, user2);

        var result = repository.getByBookId(book2.getId());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result).contains(user1);
    }
}
