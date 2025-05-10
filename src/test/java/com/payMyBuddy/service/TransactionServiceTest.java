package com.payMyBuddy.service;

import com.payMyBuddy.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private TransactionService transactionService;

    @Test
    public void testGetTransactions() throws Exception {
        Iterable<Transaction> allTransactions = transactionService.getTransactions();
        int counter = 0;
        for (Transaction transaction : allTransactions) {
            counter ++;
        }
        assertThat(allTransactions).isNotNull();
        Assertions.assertEquals(5, counter);
    }

    @Test
    public void testGetTransactionById() throws Exception {
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(1);
        Transaction transaction = transactionOptional.get();
        assertThat(transaction).isNotNull();
        Assertions.assertEquals(1, transaction.getSender());
        Assertions.assertEquals(2, transaction.getReceiver());
        Assertions.assertEquals("restaurant", transaction.getDescription());
        Assertions.assertEquals(25, transaction.getAmount());
    }

    @Test
    public void testAddTransaction() throws Exception {
        Transaction transactionNew = transactionService.addTransaction(4, 3, "note de frais", 120);
        assertThat(transactionNew).isNotNull();
        Assertions.assertEquals(4, transactionNew.getSender());
        Assertions.assertEquals(3, transactionNew.getReceiver());
        Assertions.assertEquals("note de frais", transactionNew.getDescription());
        Assertions.assertEquals(120, transactionNew.getAmount());
    }

    @Test
    public void testDeleteTransactionById() throws Exception {
        Iterable<Transaction> allTransactions = transactionService.getTransactions();
        int lastId = 0;
        int counter = 0;
        for (Transaction transaction : allTransactions) {
            lastId = transaction.getId();
            counter ++;
        }
        counter --;
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(lastId);
        Transaction transaction = transactionOptional.get();
        transactionService.deleteTransactionById(transaction.getId());
        assertThat(allTransactions).isNotNull();
        Assertions.assertEquals(4, counter);
    }

    @Test
    public void testFormTransactionValidationDescriptionEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionService.formTransactionValidation("", 20.0);
        });
        String expectedMessage = "Vous devez donner une description de la transaction !";
        String actualMessgae = exception.getMessage();
        Assertions.assertTrue(actualMessgae.contains(expectedMessage));
    }

    @Test
    public void testFormTransactionValidationAmountIsZero() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionService.formTransactionValidation("note de frais", 0.0);
        });
        String expectedMessage = "Vous devez donner un montant à la transaction !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
