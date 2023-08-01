package com.tutorial.books.repository;

import com.tutorial.books.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    public List<Book> getAll();

    public Optional<Book> getById(Integer id);

    public List<Book> getByUserId(Integer userId);

    Book create(Book book);

    void delete(Integer id);

    void update(Book book);
}
