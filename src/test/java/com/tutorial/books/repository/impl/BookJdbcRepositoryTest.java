package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
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
public class BookJdbcRepositoryTest extends TutorialBooksRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void clearBooks() {
        clearTables();
    }

    @Test
    void testGetAll() {
        var book1 = createBook();
        var book2 = createBook();
        var book3 = createBook();

        var result = repository.getAll();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result).contains(book1);
        assertThat(result).contains(book2);
        assertThat(result).contains(book3);
    }

    @Test
    void testGetById() {
        createBook();
        createBook();
        var book = createBook();

        var result = repository.getById(book.getId());

        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    void testGetByUserId() {
        createUser();
        var user = createUser();

        createBook();
        var book1 = createBook();
        var book2 = createBook();

        giveBookToUser(book1, user);
        giveBookToUser(book2, user);

        var result = repository.getByUserId(user.getId());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(book1);
        assertThat(result).contains(book2);
    }

    @Test
    void testCreate() {
        var book = Book.builder()
                .name("aboba")
                .publishYear(32767)
                .author("sus")
                .quantityAvailable(1)
                .build();

        var result = repository.getById(repository.create(book).getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    void testDelete() {
        var book = createBook();

        repository.delete(book.getId());

        var result = repository.getById(book.getId());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void testUpdate() {
        var book = createBook();
        book.setName("aboba");
        book.setPublishYear(11);
        repository.update(book);

        var result = repository.getById(book.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(book);
    }
}
