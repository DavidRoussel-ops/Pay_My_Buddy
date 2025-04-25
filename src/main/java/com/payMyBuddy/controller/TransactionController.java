package com.payMyBuddy.controller;

import com.payMyBuddy.model.Transaction;
import com.payMyBuddy.model.User;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.TransactionService;
import com.payMyBuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SecurityService securityService;

    /**
     * Controller de la page transaction
     */
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

    /**
     * Controller de la méthode post transaction
     * @param email
     * @param description
     * @param amount
     * @param redirectAttributes
     * @return transaction
     */
    @PostMapping("/transaction")
    public String populateList(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "amount", defaultValue = "0.0") Double amount,
            RedirectAttributes redirectAttributes
    ) {
        UserDetails userDetails = securityService.getCurrentUserDetails();
        try {
            User userSender = userService.getUserByEmail(userDetails.getUsername());
            User userReceiver = userService.getUserByEmail(email);
            if (userReceiver != null) {
                transactionService.addTransaction(userSender.getId(), userReceiver.getId(), description, amount);
                logger.info("Information de la transaction : {} {} {}", userReceiver.getUsername(), description, amount);
                return "redirect:/transaction";
            } else {
                throw new IllegalArgumentException("Veuillez selectionnez une relation valide.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/transaction";
        }
    }
}
