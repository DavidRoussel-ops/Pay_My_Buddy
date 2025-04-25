package com.payMyBuddy.repository;

import com.payMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Méthode permettant de retrouver un utilisateur dans la bdd via son adresse mail
     * @param email
     * @return Utilisateur enregistrer en BDD
     */
    User findByEmail(String email);

}
