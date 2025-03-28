package com.payMyBuddy.service;

import com.payMyBuddy.model.Transaction;
import com.payMyBuddy.model.User;
import com.payMyBuddy.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public Transaction addTransaction(int userId, int userFriendId, String description, double amount) {
        if (formTransactionValidation(userFriendId, description, amount)) {
            Transaction transaction = new Transaction();
            transaction.setSender(userId);
            transaction.setReceiver(userFriendId);
            transaction.setDescription(description);
            transaction.setAmount(amount);
            logger.info("La transaction à bien étais enregistrer.");
            return transactionRepository.save(transaction);
        } else {
            throw new IllegalArgumentException("La transaction n'as pas abouti.");
        }
    }

    public void deleteTransactionById(Integer id) {
        transactionRepository.deleteById(id);
    }

    public boolean formTransactionValidation(int userFriendId, String description, Double amount) {
        Optional<User> usersIdInBDD = userService.getUserById(userFriendId);
        User userFound = usersIdInBDD.get();
        if (userFound.getEmail() == null) {
            logger.warn("L'adresse mail renseigner ne corresponds à aucune personnes connue de l'application !");
            return false;
        }
        if (description == null || description.isEmpty()) {
            logger.warn("Vous devez donner une description de la transaction !");
            return false;
        }
        if (amount.isNaN() || amount == 0.0) {
            logger.warn("Vous devez donner un montant à la transaction !");
            return false;
        }
        return true;
    }
}
