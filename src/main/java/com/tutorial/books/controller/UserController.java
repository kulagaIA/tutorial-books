package com.tutorial.books.controller;


import com.tutorial.books.dto.UserCreateDTO;
import com.tutorial.books.dto.UserUpdateDTO;
import com.tutorial.books.entity.User;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import com.tutorial.books.util.security.annotations.AllowAdmin;
import com.tutorial.books.util.security.annotations.AllowOwningUserOrAdmin;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.tutorial.books.util.Constants.ALL_CONFIRMATION_TYPES;
import static com.tutorial.books.util.Constants.HTTP_STATUS_UNPROCESSABLE_CONTENT;

@Controller
@EnableMethodSecurity
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllThatCurrentUserIsAllowedToView());
        return "users/users";
    }

    @AllowOwningUserOrAdmin
    @GetMapping("/users/{id}")
    public String showUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("userBooks", bookService.getByUserId(id));
        return "users/user";
    }

    @AllowAdmin
    @GetMapping("/users/new")
    public String showNewUserPage(Model model) {
        model.addAttribute("userCreateDTO", new UserCreateDTO());
        model.addAttribute("allConfirmationTypes", ALL_CONFIRMATION_TYPES);
        return "users/new";
    }

    @AllowAdmin
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute @Valid
                             UserCreateDTO userCreateDTO,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("allConfirmationTypes", ALL_CONFIRMATION_TYPES);
            response.setStatus(HTTP_STATUS_UNPROCESSABLE_CONTENT);
            return "users/new";
        }

        userService.create(mapper.map(userCreateDTO, User.class));

        return "redirect:/users";
    }

    @AllowOwningUserOrAdmin
    @GetMapping("/users/{id}/delete")
    public String deleteUser(Model model, @PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @AllowOwningUserOrAdmin
    @GetMapping("/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.getById(id));
        return "users/edit";
    }

    @AllowOwningUserOrAdmin
    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable("id")
                             Integer id,
                             @ModelAttribute @Valid
                             UserUpdateDTO userUpdateDTO,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse response) {
        var user = mapper.map(userUpdateDTO, User.class);
        user.setId(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            response.setStatus(HTTP_STATUS_UNPROCESSABLE_CONTENT);
            return "users/edit";
        }

        userService.update(user);

        return "redirect:/users";
    }
}
