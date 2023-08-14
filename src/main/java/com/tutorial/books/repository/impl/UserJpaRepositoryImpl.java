package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJpaRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public UserJpaRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<User> getAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public Optional<User> getById(Integer id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public List<User> getByBookId(Integer bookId) {
        return null;
    }

    @Override
    public User create(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public void update(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public List<User> getWithoutBookByBookId(Integer bookId) {
        return null;
    }
}
