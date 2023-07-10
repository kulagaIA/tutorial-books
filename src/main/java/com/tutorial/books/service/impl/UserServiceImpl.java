package com.tutorial.books.service.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import com.tutorial.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getById(Integer id) {
        return userRepository.getById(id);
    }
}
