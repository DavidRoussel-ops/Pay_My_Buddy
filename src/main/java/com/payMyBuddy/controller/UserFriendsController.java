package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.model.UserFriends;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserFriendsService;
import com.payMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserFriendsController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFriendsService userFriendsService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("/relationship")
    public String addUserFriends(
            @RequestParam(value = "email") String email
    ) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        try {
            User userExcisting = userService.getUserByEmail(userDetails.getUsername());
            User userMail = userService.getUserByEmail(email);
            UserFriends userFriends = new UserFriends();
            userFriends.setUserId(userExcisting.getId());
            userFriends.setUserFriends(userMail.getId());
            userFriendsService.addUserFriends(userFriends);
            return "redirect:/relationship";
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
            return "redirect:/relationship";
        }
    }
}
