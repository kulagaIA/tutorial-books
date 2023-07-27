package com.tutorial.books.repository;

import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Random;

import static com.tutorial.books.util.Constants.*;

public class TutorialBooksRepositoryTest {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected void deleteAllBooks() {
        jdbcTemplate.execute("delete from " + TABLE_NAME_BOOKS);
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
        jdbcTemplate.execute("delete from " + TABLE_NAME_USERS);
    }

    protected User createUser() {
        var user = User.builder()
                .birthYear(new Random().nextInt())
                .name(RandomStringUtils.randomAlphabetic(10))
                .build();

        user.setId(insertUser(user));

        return user;
    }

    protected void giveBookToUser(Book book, User user) {
        jdbcTemplate.update("insert into " + TABLE_NAME_USERS_BOOKS + " values (?, ?)",
                user.getId(),
                book.getId());
    }

    protected Book createBook() {
        var book = Book.builder()
                .publishYear(new Random().nextInt())
                .name(RandomStringUtils.randomAlphabetic(10))
                .author(RandomStringUtils.randomAlphabetic(20))
                .quantityAvailable(new Random().nextInt(5))
                .build();

        book.setId(insertBook(book));

        return book;
    }

    protected Integer insertBook(Book book) {
        var simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME_BOOKS).usingGeneratedKeyColumns(COLUMN_NAME_BOOKS_ID);

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(book);

        return (int) simpleJdbcInsert.executeAndReturnKey(paramSource);
    }

    protected Integer insertUser(User user) {
        var simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME_USERS).usingGeneratedKeyColumns(COLUMN_NAME_USERS_ID);

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);

        return (int) simpleJdbcInsert.executeAndReturnKey(paramSource);
    }
}
