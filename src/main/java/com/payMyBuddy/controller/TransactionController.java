package com.payMyBuddy.controller;

import com.payMyBuddy.model.Transaction;
import com.payMyBuddy.model.User;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.TransactionService;
import com.payMyBuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/transaction")
    public ModelAndView transaction() {
        ModelAndView modelAndView = new ModelAndView("transaction");
        UserDetails userDetails = securityService.getCurrentUserDetails();
        User userConnect = userService.getUserByEmail(userDetails.getUsername());
        List<User> usersFriendsList = userConnect.getConnections();
        List<String> emailFriendsList = new ArrayList<>();
        for (User userFriends : usersFriendsList) {
            String emailFriends = userFriends.getEmail();
            emailFriendsList.add(emailFriends);
            modelAndView.addObject("emailFriendsList", emailFriendsList);
        }
        return modelAndView;
    }

    @PostMapping("/transaction")
    public String populateList(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "amount") double amount
    ) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        try {
            User userSender = userService.getUserByEmail(userDetails.getUsername());
            User userReceiver = userService.getUserByEmail(email);
            Transaction transaction = new Transaction();
            transaction.setSender(userSender.getId());
            transaction.setReceiver(userReceiver.getId());
            transaction.setDescription(description);
            transaction.setAmount(amount);
            transactionService.addTransaction(transaction);
            return "redirect:/transaction";
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
            return "redirect:/transaction";
        }
    }
}
