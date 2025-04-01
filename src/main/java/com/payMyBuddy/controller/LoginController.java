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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getLogin(
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        try {
            if (!securityService.isAuthenticated()) {
                return "login";
            }
            UserDetails userDetails = securityService.getCurrentUserDetails();
            if (userDetails == null) {
                redirectAttributes.addFlashAttribute("error", new IllegalArgumentException("Pas de bol"));
                return "login";
            }
            User user = userService.getUserByEmail(userDetails.getUsername());
            session.setAttribute("user", user);
            logger.info("Utilisateur connecter : {}", user);
            return "login";
        } catch (IllegalArgumentException illegalArgumentException) {
            redirectAttributes.addFlashAttribute("error", illegalArgumentException.getMessage());
            return "redirect:/login";
        }
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
