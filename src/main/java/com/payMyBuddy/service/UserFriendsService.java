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
    UserFriendsRepository userFriendsRepository;

    /**
     * Méthode renvoyant la liste des relations dans la BDD
     * @return Iterable<UserFriends>
     */
    public Iterable<UserFriends> getUserFriends() {
        return userFriendsRepository.findAll();
    }

    /**
     * Méthode renvoyant une relation par son ID
     * @param id
     * @return Optional<UserFriends>
     */
    public Optional<UserFriends> getUserFriendsById(Integer id) {
        return userFriendsRepository.findById(id);
    }

    /**
     * Méthode d'ajout de relation
     * @param userId
     * @param userFriendsId
     * @return UserFriends
     */
    @Transactional
    public UserFriends addUserFriends(int userId, int userFriendsId) {
        Iterable<UserFriends> userFriendsInBDD = getUserFriends();
        try {
            UserFriends userFriends = new UserFriends();
            userFriends.setUserId(userId);
            userFriends.setUserFriends(userFriendsId);
            for (UserFriends userFriendInBDD : userFriendsInBDD) {
                if (userFriends.getUserId() == userFriendInBDD.getUserId() && userFriends.getUserFriends() == userFriendInBDD.getUserFriends()) {
                    throw new IllegalArgumentException("Vous avez déjà ajouter cette personne à votre liste de connexion.");
                }
            }
            logger.info("Relation bien enregistrer.");
            return userFriendsRepository.save(userFriends);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Méthode de suppression d'une relation par son ID
     * @param id
     */
    public void deleteUserFriendsById(Integer id) {
        userFriendsRepository.deleteById(id);
    }
}
