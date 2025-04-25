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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    /**
     * Controller de la méthode post de registration
     * @param username
     * @param password
     * @param email
     * @param redirectAttributes
     * @return page de login en succès/ registration en cas d'échec
     */
    @PostMapping("/registration")
    public String addUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "email") String email,
            RedirectAttributes redirectAttributes
    ) {
        try {
            userService.addUser(username, password, email);
            logger.info("Utilisateur bien enregistrer");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registration";
        }
    }

    /**
     * Controller de la méthode post updateUser
     * @param username
     * @param email
     * @param password
     * @param session
     * @param redirectAttributes
     * @return page de profil
     */
    @PostMapping("/profil")
    public String updateUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        try {
            User userExcisting = userService.getUserByEmail(userDetails.getUsername());
            userExcisting.setUsername(username);
            userExcisting.setEmail(email);
            userExcisting.setPassword(password);
            userService.updateUser(userExcisting);
            session.setAttribute("user", userExcisting);
            securityService.updateSpringSecurityContext(userExcisting);
            logger.info("Utilisateur bien modifier.");
            return "redirect:/profil";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profil";
        }
    }
}
