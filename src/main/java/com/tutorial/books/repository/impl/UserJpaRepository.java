package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    public List<User> findByBooksId(Integer bookId);

    public List<User> findDistinctByBooksIdNot(Integer bookId);
}
