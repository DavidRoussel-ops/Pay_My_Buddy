package com.payMyBuddy.repository;

import com.payMyBuddy.model.UserFriends;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFriendsRepository extends CrudRepository<UserFriends, Integer> {
}
