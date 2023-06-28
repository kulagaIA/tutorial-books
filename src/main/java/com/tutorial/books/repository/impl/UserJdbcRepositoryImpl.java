package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJdbcRepositoryImpl implements UserRepository {
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> getById() {
        return Optional.empty();
    }
}
