package com.tutorial.books.controller;


import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users/users";
    }

    @GetMapping("/user/{id}")
    public String showUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("userBooks", bookService.getByUserId(id));
        return "users/user";
    }
}
