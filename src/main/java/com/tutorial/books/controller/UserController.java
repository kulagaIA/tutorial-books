package com.tutorial.books.controller;


import com.tutorial.books.entity.User;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.tutorial.books.util.Constants.*;

@Controller
@EnableMethodSecurity
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
    public String createUser(@ModelAttribute @Valid User user,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            response.setStatus(HTTP_STATUS_UNPROCESSABLE_CONTENT);
            return "users/new";
        }
        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @PreAuthorize("hasRole('" + ADMIN + "')")
    @GetMapping("/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getById(id));
        return "users/edit";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable("id") Integer id,
                             @ModelAttribute @Valid User user,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            response.setStatus(HTTP_STATUS_UNPROCESSABLE_CONTENT);
            return "users/edit";
        }
        user.setId(id);
        userService.update(user);
        return "redirect:/users";
    }
}
