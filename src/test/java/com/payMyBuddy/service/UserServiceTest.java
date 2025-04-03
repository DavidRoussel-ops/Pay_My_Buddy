package com.payMyBuddy.service;

import com.payMyBuddy.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private UserService userService;

    @Test
    public void testGetUsers() throws Exception {
        Iterable<User> allUsers = userService.getUsers();
        int counter = 0;
        for (User user : allUsers) {
            counter ++;
        }
        assertThat(allUsers).isNotNull();
        Assertions.assertEquals(5, counter);
    }

    @Test
    public void testUserById() throws Exception {
        Optional<User> users = userService.getUserById(1);
        User user = users.get();
        assertThat(user).isNotNull();
        Assertions.assertEquals("Jaques", user.getUsername());
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        Optional<User> users = userService.getUserById(1);
        User user = users.get();
        User userToTest = userService.getUserByEmail("jaques@gmail.com");
        assertThat(userToTest).isNotNull();
        Assertions.assertEquals(userToTest.getEmail(), user.getEmail());
    }

    @Test
    public void testAddUser() throws Exception {
        User userNew = userService.addUser("test2", "test", "test2@gmail.com");
        assertThat(userNew).isNotNull();
        Assertions.assertEquals("test2", userNew.getUsername());
    }

    @Test
    public void testUpdateUser() throws Exception {
        Optional<User> users = userService.getUserById(2);
        User user = users.get();
        user.setUsername("Pierrot");
        userService.updateUser(user);
        assertThat(user).isNotNull();
        Assertions.assertEquals("Pierrot", user.getUsername());
    }

    @Test
    public void testDeleteUser() throws Exception {
        Iterable<User> allUsers = userService.getUsers();
        int lastId = 0;
        int counter = 0;
        for (User user : allUsers) {
            lastId = user.getId();
            counter ++;
        }
        Optional<User> users = userService.getUserById(lastId);
        User user = users.get();
        userService.deleteUserById(user.getId());
        assertThat(allUsers).isNotNull();
        Assertions.assertEquals(5, counter);
    }
}
