package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/profil")
    public String getUser(HttpSession session) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        User user = userService.getUserByEmail(userDetails.getUsername());
        session.setAttribute("user", user);
        return "profil";
    }

    @GetMapping("/login")
    public String getLogin(HttpSession session) {
        if (!securityService.isAuthenticated()) {
            System.out.println("Utilisateur non reconnu.");
            return "login";
        }
        UserDetails userDetails = securityService.getCurrentUserDetails();
        if (userDetails == null) {
            System.out.println("Les détails utilisateur ne sont pas reconnu");
            return "login";
        }
        User user = userService.getUserByEmail(userDetails.getUsername());
        session.setAttribute("user", user);
        return "profil";
    }

    @GetMapping("/logout")
    public String getLogout() {
        return "Merci d'être passé.";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "registration";
    }

    @GetMapping("/relationship")
    public String getRelationShip() {
        return "relationship";
    }

    @GetMapping("/transaction")
    public String getTransaction() {
        return "transaction";
    }
}
