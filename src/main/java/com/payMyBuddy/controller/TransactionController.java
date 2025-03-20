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
import java.util.Optional;

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
        Iterable<Transaction> transactions = transactionService.getTransactions();
        List<User> usersFriendsList = userConnect.getConnections();
        List<String> emailFriendsList = new ArrayList<>();
        List<String> receivers = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<Double> amounts = new ArrayList<>();
        for (User userFriends : usersFriendsList) {
            String emailFriends = userFriends.getEmail();
            emailFriendsList.add(emailFriends);
            modelAndView.addObject("emailFriendsList", emailFriendsList);
        }
        for (Transaction transaction : transactions) {
            Integer idConnect = transaction.getSender();
            if (idConnect == userConnect.getId()) {
                Optional<User> userReceiverId = userService.getUserById(transaction.getReceiver());
                User userReceiver = userReceiverId.get();
                String receiver = userReceiver.getUsername();
                String description = transaction.getDescription();
                Double amount = transaction.getAmount();
                receivers.add(receiver);
                descriptions.add(description);
                amounts.add(amount);
                modelAndView.addObject("receivers", receivers);
                modelAndView.addObject("descriptions", descriptions);
                modelAndView.addObject("amounts", amounts);
            }
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
