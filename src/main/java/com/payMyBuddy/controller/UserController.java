package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String addUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            userService.addUser(user);
            System.out.println("Utilisateur enregister nom : " + user.getUsername() + " mail : " + user.getEmail() + " mot de passe " + user.getPassword() + " .");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
            return "redirect:/registration";
        }
    }
}
