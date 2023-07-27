package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.tutorial.books.util.Constants.*;
import static com.tutorial.books.util.Constants.TABLE_NAME_USERS_BOOKS;

@Repository
public class UserJdbcRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private BeanPropertyRowMapper<User> mapper = new BeanPropertyRowMapper<>(User.class);

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from " + TABLE_NAME_USERS, mapper);
    }

    @Override
    public Optional<User> getById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from users where id = ?", mapper, id));
    }

    @Override
    public List<User> getByBookId(Integer bookId) {
        return jdbcTemplate.query(
                "select * from " + TABLE_NAME_USERS +
                        " left join " + TABLE_NAME_USERS_BOOKS +
                        " on " + TABLE_NAME_USERS + "." + COLUMN_NAME_USERS_ID + "=" + TABLE_NAME_USERS_BOOKS + ".user_id" +
                        " where " + TABLE_NAME_USERS_BOOKS + ".book_id = ?",
                mapper,
                bookId);
    }
}
