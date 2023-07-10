package com.tutorial.books.repository;

import com.tutorial.books.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Random;

import static com.tutorial.books.util.Constants.COLUMN_NAME_USERS_ID;
import static com.tutorial.books.util.Constants.TABLE_NAME_USERS;

public class TutorialBooksRepositoryTest {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

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

    protected Integer insertUser(User user) {
        var simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME_USERS).usingGeneratedKeyColumns(COLUMN_NAME_USERS_ID);

        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);

        return (int) simpleJdbcInsert.executeAndReturnKey(paramSource);
    }
}
