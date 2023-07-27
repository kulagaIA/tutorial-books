package com.tutorial.books.controller;


import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "books/books";
    }

    @GetMapping("/books/{id}")
    public String showBook(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("users", userService.getByBookId(id));
        return "books/book";
    }

}
