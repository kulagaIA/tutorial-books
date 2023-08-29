package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImplJpa implements BookRepository {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Override
    public List<Book> getAll() {
        return bookJpaRepository.findAll();
    }

    @Override
    public Optional<Book> getById(Integer id) {
        return bookJpaRepository.findById(id);
    }

    @Override
    public List<Book> getByUserId(Integer userId) {
        return null;
    }

    @Override
    public Book create(Book book) {
        return bookJpaRepository.save(book);
    }

    @Override
    public void delete(Integer id) {
        bookJpaRepository.deleteById(id);
    }

    @Override
    public void update(Book book) {
        bookJpaRepository.save(book);
    }

    @Override
    public void bindToUser(Integer bookId, Integer userId) {
    }

    @Override
    public void decreaseQuantityAvailable(Integer bookId) {
        var book = bookJpaRepository.findById(bookId).orElseThrow();
        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        bookJpaRepository.save(book);
    }

    @Override
    public void unbindFromUser(Integer bookId, Integer userId) {
    }

    @Override
    public void increaseQuantityAvailable(Integer id) {
        var book = bookJpaRepository.findById(id).orElseThrow();
        book.setQuantityAvailable(book.getQuantityAvailable() + 1);
        bookJpaRepository.save(book);
    }
}