package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<Book, Integer> {
}
