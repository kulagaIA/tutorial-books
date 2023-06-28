package com.tutorial.books.repository.impl;

import com.tutorial.books.repository.TutorialBooksRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserJdbcRepositoryTest extends TutorialBooksRepositoryTest {

    @Autowired
    private UserJdbcRepositoryImpl repository;

    @Test
    void testGetAll() {

    }

}
