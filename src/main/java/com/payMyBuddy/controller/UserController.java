package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

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

    @PostMapping("/profil")
    public String updateUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            HttpSession session
    ) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        try {
            User userExcisting = userService.getUserByEmail(userDetails.getUsername());
            if (username != null && !username.isEmpty()) {
                userExcisting.setUsername(username);
            }
            if (email != null && !email.isEmpty()) {
                userExcisting.setEmail(email);
            }
            if (password != null && !password.isEmpty()) {
                userExcisting.setPassword(password);
            }
            userService.addUser(userExcisting);
            session.setAttribute("user", userExcisting);
            securityService.updateSpringSecurityContext(userExcisting);
            return "redirect:/profil";
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
            return "redirect:/profil";
        }
    }
}
