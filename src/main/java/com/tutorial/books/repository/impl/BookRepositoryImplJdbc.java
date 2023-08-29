package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.tutorial.books.util.Constants.*;

public class BookRepositoryImplJdbc implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private BeanPropertyRowMapper<Book> mapper = new BeanPropertyRowMapper<>(Book.class);

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select * from " + TABLE_NAME_BOOKS, mapper);
    }

    @Override
    public Optional<Book> getById(Integer id) {
        Book book;
        try {
            book = jdbcTemplate.queryForObject(
                    "select * from books " +
                            "where " + COLUMN_NAME_BOOKS_ID + " = ?",
                    mapper,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> getByUserId(Integer userId) {
        return jdbcTemplate.query(
                "select * from " + TABLE_NAME_BOOKS +
                        " left join " + TABLE_NAME_USERS_BOOKS +
                        " on " + TABLE_NAME_BOOKS + "." + COLUMN_NAME_BOOKS_ID + "=" + TABLE_NAME_USERS_BOOKS + ".book_id" +
                        " where " + TABLE_NAME_USERS_BOOKS + ".user_id = ?",
                mapper,
                userId);
    }

    @Override
    public Book create(Book book) {
        var simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME_BOOKS).usingGeneratedKeyColumns(COLUMN_NAME_BOOKS_ID);

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(book);

        book.setId((int) simpleJdbcInsert.executeAndReturnKey(paramSource));

        return book;
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("delete from " + TABLE_NAME_BOOKS + " where id = ?", id);
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update("update " + TABLE_NAME_BOOKS + " " +
                        "set name = ?, " +
                        "publish_year = ?, " +
                        "author = ?, " +
                        "quantity_available = ? " +
                        "where id = ?",
                book.getName(),
                book.getPublishYear(),
                book.getAuthor(),
                book.getQuantityAvailable(),
                book.getId());
    }

    @Override
    public void bindToUser(Integer bookId, Integer userId) {
        jdbcTemplate.update(
                "insert into users_books values (?, ?)",
                userId,
                bookId);
    }

    @Override
    public void decreaseQuantityAvailable(Integer bookId) {
        jdbcTemplate.update(
                "update books " +
                        "set quantity_available = quantity_available - 1 " +
                        "where id = ?",
                bookId);
    }

    @Override
    public void unbindFromUser(Integer bookId, Integer userId) {
        jdbcTemplate.update("delete from users_books where user_id = ? and book_id = ?", userId, bookId);
    }

    @Override
    public void increaseQuantityAvailable(Integer bookId) {
        jdbcTemplate.update(
                "update books " +
                        "set quantity_available = quantity_available + 1 " +
                        "where id = ?",
                bookId);
    }
}
