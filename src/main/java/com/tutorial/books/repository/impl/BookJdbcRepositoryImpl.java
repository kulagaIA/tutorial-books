package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.tutorial.books.util.Constants.*;

@Repository
public class BookJdbcRepositoryImpl implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private BeanPropertyRowMapper<Book> mapper = new BeanPropertyRowMapper<>(Book.class);

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select * from " + TABLE_NAME_BOOKS, mapper);
    }

    @Override
    public Optional<Book> getById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from books " +
                        "where " + COLUMN_NAME_USERS_ID + " = ?",
                mapper,
                id));
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
}
