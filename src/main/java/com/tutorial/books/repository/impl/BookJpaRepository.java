package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<Book, Integer> {
    public List<Book> findByUsersId(Integer userId);
}
