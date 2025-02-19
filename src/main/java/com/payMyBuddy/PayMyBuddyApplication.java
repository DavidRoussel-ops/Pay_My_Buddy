package com.payMyBuddy;

import com.payMyBuddy.model.Transaction;
import com.payMyBuddy.model.User;
import com.payMyBuddy.service.TransactionService;
import com.payMyBuddy.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("L'application est lancer");

		/*Iterable<User> users = userService.getUsers();
		users.forEach(user -> System.out.println(user.getUsername()));

		Iterable<Transaction> transactions = transactionService.getTransactions();
		transactions.forEach(transaction -> System.out.println(transaction.getDescription()));

		Optional<User> optionalUser = userService.getUserById(1);
		User userId1 = optionalUser.get();

		System.out.println(userId1.getUsername());

		userId1.getSender().forEach(transaction -> System.out.println(transaction.getSender()));

		Optional<Transaction> optionalTransaction = transactionService.getTransactionById(1);
		Transaction transactionId1 = optionalTransaction.get();
		System.out.println(transactionId1.getDescription());*/

		Optional<User> optionalUser = userService.getUserById(2);
		User userId1 = optionalUser.get();

		userId1.getConnections().forEach(
				user -> System.out.println(user.getUsername())
		);
	}

}
