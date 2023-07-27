package com.tutorial.books.service;

import com.tutorial.books.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAll();

    public User getById(Integer id);

    public List<User> getByBookId(Integer bookId);
}
