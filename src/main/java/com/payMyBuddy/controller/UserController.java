package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String addUser(User user) {
        try {
            userService.addUser(user);
            System.out.println("Utilisateur enregister nom : " + user.getUsername() + " mail : " + user.getEmail() + " mot de passe " + user.getPassword() + " .");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
            return "redirect:/registration";
        }
    }
}
