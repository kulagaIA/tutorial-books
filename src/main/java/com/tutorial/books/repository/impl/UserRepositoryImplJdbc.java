//package com.tutorial.books.repository.impl;
//
//import com.tutorial.books.entity.User;
//import com.tutorial.books.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//
//import javax.sql.DataSource;
//import java.util.List;
//import java.util.Optional;
//
//import static com.tutorial.books.util.Constants.*;
//
//
//public class UserRepositoryImplJdbc implements UserRepository {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private DataSource dataSource;
//
//    private BeanPropertyRowMapper<User> mapper = new BeanPropertyRowMapper<>(User.class);
//
//    @Override
//    public List<User> getAll() {
//        return jdbcTemplate.query("select * from " + TABLE_NAME_USERS, mapper);
//    }
//
//    @Override
//    public Optional<User> getById(Integer id) {
//        User user;
//        try {
//            user = jdbcTemplate.queryForObject("select * from users where id = ?", mapper, id);
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//        return Optional.of(user);
//    }
//
//    @Override
//    public List<User> getByBookId(Integer bookId) {
//        return jdbcTemplate.query(
//                "select * from " + TABLE_NAME_USERS +
//                        " left join " + TABLE_NAME_USERS_BOOKS +
//                        " on " + TABLE_NAME_USERS + "." + COLUMN_NAME_USERS_ID + "=" + TABLE_NAME_USERS_BOOKS + ".user_id" +
//                        " where " + TABLE_NAME_USERS_BOOKS + ".book_id = ?",
//                mapper,
//                bookId);
//    }
//
//    @Override
//    public User create(User user) {
//        var simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
//                .withTableName(TABLE_NAME_USERS).usingGeneratedKeyColumns(COLUMN_NAME_USERS_ID);
//
//        BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
//
//        user.setId((int) simpleJdbcInsert.executeAndReturnKey(paramSource));
//
//        return user;
//    }
//
//    @Override
//    public void delete(Integer id) {
//        jdbcTemplate.update("delete from " + TABLE_NAME_USERS + " where id = ?", id);
//    }
//
//    @Override
//    public void update(User user) {
//        jdbcTemplate.update("update " + TABLE_NAME_USERS + " set name = ?, birth_year = ? where id = ?",
//                user.getUsername(),
//                user.getBirthYear(),
//                user.getId() );
//    }
//
//    @Override
//    public List<User> getWithoutBookByBookId(Integer bookId) {
//        return jdbcTemplate.query(
//                "select distinct users.* " +
//                        "from users " +
//                        "where users.id not in (select users_books.user_id " +
//                        "                       from users_books " +
//                        "                       where users_books.book_id = ?)",
//                mapper,
//                bookId);
//    }
//
//}
