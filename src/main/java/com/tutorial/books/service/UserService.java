package com.tutorial.books.service;

import com.tutorial.books.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAll();

    public Optional<User> getById();

}
