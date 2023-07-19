package com.tutorial.books.service;

import com.tutorial.books.entity.Book;

import java.util.List;

public interface BookService {

    public List<Book> getAll();

    public Book getById(Integer id);

    public List<Book> getByUserId(Integer userId);

}
