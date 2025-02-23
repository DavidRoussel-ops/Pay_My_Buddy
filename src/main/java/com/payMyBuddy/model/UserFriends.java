package com.payMyBuddy.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_FRIENDS")
public class UserFriends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "USER_FRIENDS")
    private int userFriends;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(int userFriends) {
        this.userFriends = userFriends;
    }
}
