package com.payMyBuddy.service;

import com.payMyBuddy.model.User;
import com.payMyBuddy.model.UserFriends;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserFriendsServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    UserFriendsService userFriendsService;

    @Test
    public void testGetUsers() throws Exception {
        Iterable<UserFriends> allUsersFriends = userFriendsService.getUserFriends();
        int counter = 0;
        for (UserFriends userFriend : allUsersFriends) {
            counter ++;
        }
        assertThat(allUsersFriends).isNotNull();
        Assertions.assertEquals(10, counter);
    }

    @Test
    public void testUserFriendsById() throws Exception {
        Optional<UserFriends> userFriends = userFriendsService.getUserFriendsById(1);
        UserFriends userFriends1 = userFriends.get();
        assertThat(userFriends1).isNotNull();
        Assertions.assertEquals(1, userFriends1.getUserId());
        Assertions.assertEquals(2, userFriends1.getUserFriends());
    }

    @Test
    public void testAddUserFriends() throws Exception {
        UserFriends newUserFriends = userFriendsService.addUserFriends(2, 3);
        assertThat(newUserFriends).isNotNull();
        Assertions.assertEquals(2, newUserFriends.getUserId());
        Assertions.assertEquals(3, newUserFriends.getUserFriends());
    }

    @Test
    public void testAddUserFriendsExistInBDD() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userFriendsService.addUserFriends(1, 2);
        });
        String expectedMessage = "Vous avez déjà ajouter cette personne à votre liste de connexion.";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDeleteUserFriendsById() throws Exception {
        Iterable<UserFriends> allUserFriendsInBDD = userFriendsService.getUserFriends();
        int lastId = 0;
        int counter = 0;
        for (UserFriends userFriends : allUserFriendsInBDD) {
            lastId = userFriends.getId();
            counter ++;
        }
        counter --;
        Optional<UserFriends> userFriendsOptional = userFriendsService.getUserFriendsById(lastId);
        UserFriends userFriends = userFriendsOptional.get();
        userFriendsService.deleteUserFriendsById(userFriends.getId());
        assertThat(allUserFriendsInBDD).isNotNull();
        Assertions.assertEquals(10, counter);
    }
}
