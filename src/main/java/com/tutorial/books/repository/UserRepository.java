package com.tutorial.books.repository;

import com.tutorial.books.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public List<User> getAll();

    public Optional<User> getById(Integer id);

    List<User> getByBookId(Integer bookId);
}
