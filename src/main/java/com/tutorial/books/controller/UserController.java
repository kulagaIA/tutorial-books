package com.tutorial.books.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class UserController {

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", new ArrayList<>());
        return "users/users";
    }

}
