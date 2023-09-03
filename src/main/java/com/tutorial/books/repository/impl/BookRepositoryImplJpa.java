package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImplJpa implements BookRepository {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private EntityManager entityManager;

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
        return bookJpaRepository.findByUsersId(userId);
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
    @Transactional
    public void bindToUser(Integer bookId, Integer userId) {
        var user = userJpaRepository.findById(userId).orElseThrow();
        user.getBooks().add(bookJpaRepository.findById(bookId).orElseThrow());
        userJpaRepository.save(user);
    }

    @Override
    public void decreaseQuantityAvailable(Integer bookId) {
        var book = bookJpaRepository.findById(bookId).orElseThrow();
        book.setQuantityAvailable(book.getQuantityAvailable() - 1);
        bookJpaRepository.save(book);
    }

    @Override
    @Transactional
    public void unbindFromUser(Integer bookId, Integer userId) {
        var user = userJpaRepository.findById(userId).orElseThrow();
        user.getBooks().remove(bookJpaRepository.findById(bookId).orElseThrow());
        userJpaRepository.save(user);
    }

    @Override
    public void increaseQuantityAvailable(Integer id) {
        var book = bookJpaRepository.findById(id).orElseThrow();
        book.setQuantityAvailable(book.getQuantityAvailable() + 1);
        bookJpaRepository.save(book);
    }
}