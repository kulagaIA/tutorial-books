package com.tutorial.books.service.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import com.tutorial.books.security.LibraryUserDetails;
import com.tutorial.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public List<User> getAllThatCurrentUserIsAllowedToView() {
        var currentUser = (LibraryUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.isAdmin())
            return userRepository.getAll();

        return List.of(userRepository.getById(currentUser.getId()).orElseThrow());
    }
}
