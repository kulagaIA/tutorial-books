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
        return bookRepository.getAll();
    }

    @Override
    public Book getById(Integer id) {
        return bookRepository.getById(id).orElseThrow();
    }

    @Override
    public List<Book> getByUserId(Integer userId) {
        return bookRepository.getByUserId(userId);
    }

    @Override
    public Book create(Book book) {
        return bookRepository.create(book);
    }

    @Override
    public void delete(Integer id) {
        bookRepository.delete(id);
    }

    @Override
    public void update(Book book) {
        bookRepository.update(book);
    }

    @Override
    public void giveToUser(Integer bookId, Integer userId) {
        bookRepository.giveToUser(bookId, userId);
    }
}
