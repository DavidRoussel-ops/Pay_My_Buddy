package com.payMyBuddy.repository;

import com.payMyBuddy.model.UserFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendsRepository extends JpaRepository<UserFriends, Integer> {
}
