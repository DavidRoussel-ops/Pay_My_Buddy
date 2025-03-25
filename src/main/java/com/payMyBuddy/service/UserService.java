package com.payMyBuddy.service;

import com.payMyBuddy.model.User;
import com.payMyBuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User addUser(User user) {
        if (formValidation(user.getUsername(), user.getPassword(), user.getEmail())) {
            user.setUsername(user.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEmail(user.getEmail());
            logger.info("Utilisateur enregistrer : {}", user);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("L'utilisateur n'as pas pue être enregistrer.");
        }
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    public boolean formValidation(String username, String password, String email) {
        List<String> emailsInBDD = new ArrayList<>();
        Iterable<User> usersMails = getUsers();
        for (User userMail : usersMails) {
            emailsInBDD.add(userMail.getEmail());
        }
        if (username == null || username.isEmpty()) {
            logger.warn("Username ne peut être vide !");
            return false;
        }
        if (password == null || password.isEmpty()) {
            logger.warn("Le mot de passe ne peut être vide !");
            return false;
        }
        if (email == null || email.isEmpty()) {
            logger.warn("L'email ne peut être vide !");
            return false;
        }
        for (String emailInBDD : emailsInBDD) {
            if (email.equals(emailInBDD)) {
                logger.warn("Cette email existe déjà !");
                return false;
            }
        }
        return true;
    }
}
