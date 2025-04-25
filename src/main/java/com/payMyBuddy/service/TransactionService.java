package com.payMyBuddy.service;

import com.payMyBuddy.model.Transaction;
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
    private TransactionRepository transactionRepository;

    /**
     * Méthode renvoyant toute les transactions
     * @return Iterable<Transaction>
     */
    public Iterable<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Méthode renvoyant une transaction par son ID
     * @param id
     * @return Optional<Transaction>
     */
    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    /**
     * Méthode d'ajout de transaction
     * @param userId
     * @param userFriendId
     * @param description
     * @param amount
     * @return Transaction
     */
    @Transactional
    public Transaction addTransaction(int userId, int userFriendId, String description, double amount) {
        if (formTransactionValidation(description, amount)) {
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

    /**
     * Méthode de suppression d'une transaction par son id
     * @param id
     */
    public void deleteTransactionById(Integer id) {
        transactionRepository.deleteById(id);
    }

    /**
     * Méthode de vérification des champs pour validation
     * @param description
     * @param amount
     * @return IllegalArgumentException
     */
    public boolean formTransactionValidation(String description, double amount) {
        if (description == null || description.isEmpty()) {
            logger.warn("Vous devez donner une description de la transaction !");
            throw new IllegalArgumentException("Vous devez donner une description de la transaction !");
        }
        if (amount == 0.0) {
            logger.warn("Vous devez donner un montant à la transaction !");
            throw new IllegalArgumentException("Vous devez donner un montant à la transaction !");
        }
        return true;
    }
}
