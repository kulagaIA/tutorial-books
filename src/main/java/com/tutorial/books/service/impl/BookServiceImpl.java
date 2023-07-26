package com.tutorial.books.service.impl;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
import com.tutorial.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Book getById(Integer id) {
        return null;
    }

    @Override
    public List<Book> getByUserId(Integer userId) {
        return bookRepository.getByUserId(userId);
    }
}
