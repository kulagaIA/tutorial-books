package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
}
