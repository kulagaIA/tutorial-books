package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    List<User> findByBooksId(Integer bookId);

    Optional<User> findByUsername(String username);
}
