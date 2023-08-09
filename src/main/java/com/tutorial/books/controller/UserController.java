package com.tutorial.books.controller;


import com.tutorial.books.entity.User;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{id}")
    public String showUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("userBooks", bookService.getByUserId(id));
        return "users/user";
    }

    @GetMapping("/users/new")
    public String showNewUserPage(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute @Valid User user, Model model) {
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getById(id));
        return "users/edit";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@ModelAttribute @Valid User user,  @PathVariable("id") Integer id, Model model) {
        user.setId(id);
        userService.update(user);
        return "redirect:/users";
    }
}
