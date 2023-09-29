package com.tutorial.books.repository;

import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.User;
import com.tutorial.books.repository.impl.BookJpaRepository;
import com.tutorial.books.repository.impl.UserJpaRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Calendar;
import java.util.Random;

import static com.tutorial.books.util.Constants.TABLE_NAME_USERS_BOOKS;

public class TutorialBooksRepositoryTest {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected UserJpaRepository userJpaRepository;

    @Autowired
    protected BookJpaRepository bookJpaRepository;

    protected void deleteAllBooks() {
        bookJpaRepository.deleteAll();
    }

    protected void deleteUsersBooks() {
        jdbcTemplate.execute("delete from " + TABLE_NAME_USERS_BOOKS);
    }

    protected void clearTables() {
        deleteAllUsers();
        deleteAllBooks();
        deleteUsersBooks();
    }

    protected void deleteAllUsers() {
        userJpaRepository.deleteAll();
    }

    protected User createUser() {
        var user = User.builder()
                .birthYear(Calendar.getInstance().get(Calendar.YEAR) - new Random().nextInt(130))
                .username(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(20))
                .name(RandomStringUtils.randomAlphabetic(20))
                .build();

        userJpaRepository.save(user);

        return user;
    }

    protected void giveBookToUser(Book book, User user) {
        jdbcTemplate.update("insert into " + TABLE_NAME_USERS_BOOKS + " values (?, ?)",
                user.getId(),
                book.getId());
    }

    protected Book createBook() {
        var book = Book.builder()
                .publishYear(new Random().nextInt(32767))
                .name(RandomStringUtils.randomAlphabetic(10))
                .author(RandomStringUtils.randomAlphabetic(20))
                .quantityAvailable(new Random().nextInt(5) + 1)
                .build();

        bookJpaRepository.save(book);

        return book;
    }

    protected Book createBook(Book.BookBuilder builder) {
        var book = builder.build();

        bookJpaRepository.save(book);

        return book;
    }
}
