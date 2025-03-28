package com.payMyBuddy.service;

import com.payMyBuddy.model.UserFriends;
import com.payMyBuddy.repository.UserFriendsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFriendsService {

    private static final Logger logger = LoggerFactory.getLogger(UserFriendsService.class);

    @Autowired
    UserService userService;

    @Autowired
    UserFriendsRepository userFriendsRepository;

    public Iterable<UserFriends> getUserFriends() {
        return userFriendsRepository.findAll();
    }

    public Optional<UserFriends> getUserFriendsById(Integer id) {
        return userFriendsRepository.findById(id);
    }

    @Transactional
    public UserFriends addUserFriends(int userId, int userFriendsId) {
        try {
            UserFriends userFriends = new UserFriends();
            userFriends.setUserId(userId);
            userFriends.setUserFriends(userFriendsId);
            logger.info("Relation bien enregistrer.");
            return userFriendsRepository.save(userFriends);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("La relation n'as pas était ajouter.");
        }
    }
}
