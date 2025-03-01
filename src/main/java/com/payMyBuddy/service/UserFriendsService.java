package com.payMyBuddy.service;

import com.payMyBuddy.model.UserFriends;
import com.payMyBuddy.repository.UserFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFriendsService {

    @Autowired
    UserFriendsRepository userFriendsRepository;

    public Iterable<UserFriends> getUserFriends() {
        return userFriendsRepository.findAll();
    }

    public Optional<UserFriends> getUserFriendsById(Integer id) {
        return userFriendsRepository.findById(id);
    }

    public UserFriends addUserFriends(UserFriends userFriends) {
        return userFriendsRepository.save(userFriends);
    }
}
