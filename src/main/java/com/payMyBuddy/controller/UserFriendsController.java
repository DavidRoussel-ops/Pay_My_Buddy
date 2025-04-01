package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserFriendsService;
import com.payMyBuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserFriendsController {

    private static final Logger logger = LoggerFactory.getLogger(UserFriendsController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendsService userFriendsService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/relationship")
    public String addUserFriends(
            @RequestParam(value = "email") String email,
            RedirectAttributes redirectAttributes
    ) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        try {
            User userExcisting = userService.getUserByEmail(userDetails.getUsername());
            User userMail = userService.getUserByEmail(email);
            if (email.isEmpty()) {
                throw new IllegalArgumentException("Veuillez remplir le champ puis cliquer sur Ajouter.");
            }
            if (userMail != null) {
                userFriendsService.addUserFriends(userExcisting.getId(), userMail.getId());
                logger.info("Email de l'utilisateur enregistrer en relation : {}", userMail.getEmail());
            } else {
                throw new IllegalArgumentException("L'adresse mail renseigner ne corresponds à aucune personnes connue de l'application !");
            }
            return "redirect:/relationship";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/relationship";
        }
    }
}
