package com.tutorial.books.service.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import com.tutorial.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.getById(id).orElseThrow();
    }

    @Override
    public List<User> getByBookId(Integer bookId) {
        return userRepository.getByBookId(bookId);
    }

    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public List<User> getWithoutBookByBookId(Integer bookId) {
        return userRepository.getWithoutBookByBookId(bookId);
    }
}
