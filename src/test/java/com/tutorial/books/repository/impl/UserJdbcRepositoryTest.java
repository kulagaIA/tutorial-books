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
        deleteAllUsers();
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
}
