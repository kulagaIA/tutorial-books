package com.tutorial.books.service;

import com.tutorial.books.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getById(Integer id);

    List<Book> getByUserId(Integer userId);

    Book create(Book book);

    void delete(Integer id);

    void update(Book book);
}
