package com.tutorial.books.service;

import com.tutorial.books.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    List<User> getByBookId(Integer bookId);

    User create(User user);

    void delete(Integer id);

    void update(User user);

    List<User> getWithoutBookByBookId(Integer bookId);
}
