package com.payMyBuddy;

import com.payMyBuddy.model.Transaction;
import com.payMyBuddy.model.User;
import com.payMyBuddy.model.UserFriends;
import com.payMyBuddy.service.TransactionService;
import com.payMyBuddy.service.UserFriendsService;
import com.payMyBuddy.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserFriendsService userFriendsService;

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
		System.out.println(transactionId1.getSender());

		/*Optional<User> optionalUser = userService.getUserById(1);
		User userId1 = optionalUser.get();

		Optional<User> optionalUser1 = userService.getUserById(9);
		User userId9 = optionalUser1.get();

		userId1.getConnections().forEach(
				user -> System.out.println(user.getUsername())
		);

		User user5 = new User();
		user5.setUsername("Paul");
		user5.setEmail("paul@gmail.com");
		user5.setPassword("paul");

		userService.addUser(user5);

		UserFriends userFriends = new UserFriends();
		userFriends.setUserId(userId9.getId());
		userFriends.setUserFriends(userId1.getId());

		userFriendsService.addUserFriends(userFriends);
		List<User> newFriends = new ArrayList<>();
		newFriends.add(user5);

		userId1.getConnections().forEach(
				user -> user.setConnections(newFriends)
		);

		//Méthode pour update une entité
		User userExisting = userService.getUserById(4).get();
		System.out.println("user mail : " + userExisting.getEmail());

		userExisting.setEmail("michel@gmail.com");
		userService.addUser(userExisting);

		System.out.println("user mail : " + userExisting.getEmail());

		//Test suppression user
		userService.deleteUserById(3);*/

		//Test suppression transaction
		transactionService.deleteTransactionById(2);
	}

}
