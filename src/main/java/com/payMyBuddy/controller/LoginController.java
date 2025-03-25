package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/profil")
    public String getUser(HttpSession session) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        User user = userService.getUserByEmail(userDetails.getUsername());
        session.setAttribute("user", user);
        logger.info("Utilisateur : {}", user);
        return "profil";
    }

    @GetMapping("/login")
    public String getLogin(HttpSession session) {
        if (!securityService.isAuthenticated()) {
            return "login";
        }
        UserDetails userDetails = securityService.getCurrentUserDetails();
        if (userDetails == null) {
            return "login";
        }
        User user = userService.getUserByEmail(userDetails.getUsername());
        session.setAttribute("user", user);
        logger.info("Utilisateur connecter : {}", user);
        return "transaction";
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
    
}
