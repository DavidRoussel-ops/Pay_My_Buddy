package com.payMyBuddy.repository;

import com.payMyBuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Iterable<User> findByEmail(String email);

}
