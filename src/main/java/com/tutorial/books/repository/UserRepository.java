package com.tutorial.books.repository;

import com.tutorial.books.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll();

    Optional<User> getById(Integer id);

    List<User> getByBookId(Integer bookId);

    User create(User user);

    void delete(Integer id);

    void update(User user);

    List<User> getWithoutBookByBookId(Integer bookId);
}
