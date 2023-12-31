package com.tutorial.books.controller;

import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.User;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.tutorial.books.util.Constants.HTTP_STATUS_UNPROCESSABLE_CONTENT;

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
        model.addAttribute("bookUsers", userService.getByBookId(id));
        model.addAttribute("usersWithoutBook", userService.getWithoutBookByBookId(id));
        model.addAttribute("user", new User());
        return "books/book";
    }

    @GetMapping("/books/new")
    public String showNewBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping("/books/create")
    public String createBook(@ModelAttribute @Valid Book book,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            response.setStatus(HTTP_STATUS_UNPROCESSABLE_CONTENT);
            return "books/new";
        }

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
    public String updateBook(@PathVariable("id") Integer id,
                             @ModelAttribute @Valid Book book,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            response.setStatus(HTTP_STATUS_UNPROCESSABLE_CONTENT);
            return "books/edit";
        }

        book.setId(id);
        bookService.update(book);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/give")
    public String giveToUser(@ModelAttribute User user, @PathVariable("id") Integer bookId, Model model) {
        bookService.giveToUser(bookId, user.getId());
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/books/{id}/return")
    public String returnFromUser(@ModelAttribute User user, @PathVariable("id") Integer bookId, Model model) {
        bookService.returnFromUser(bookId, user.getId());
        return "redirect:/books/" + bookId;
    }
}
