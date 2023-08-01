package com.tutorial.books.controller;


import com.tutorial.books.entity.Book;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/books/new")
    public String showNewBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping("/books/create")
    public String createBook(@ModelAttribute Book book, Model model) {
        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/delete")
    public String deleteBook(Model model, @PathVariable("id") Integer id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBook(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("book", bookService.getById(id));
        return "books/edit";
    }

    @PostMapping("/books/{id}/update")
    public String updateBook(@ModelAttribute Book book,  @PathVariable("id") Integer id, Model model) {
        book.setId(id);
        bookService.update(book);
        return "redirect:/books";
    }

}
