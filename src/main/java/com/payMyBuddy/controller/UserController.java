package com.payMyBuddy.controller;

import com.payMyBuddy.configuration.SpringSecurityConfig;
import com.payMyBuddy.model.User;
import com.payMyBuddy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SpringSecurityConfig springSecurityConfig;

    @GetMapping("/user")
    public String getUser() {
        return "user";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "Merci d'être passé.";
    }
}
