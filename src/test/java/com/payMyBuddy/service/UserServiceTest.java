package com.payMyBuddy.service;

import com.payMyBuddy.model.User;
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
        Assertions.assertEquals(4, counter);
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
        counter --;
        Optional<User> users = userService.getUserById(lastId);
        User user = users.get();
        userService.deleteUserById(user.getId());
        assertThat(allUsers).isNotNull();
        Assertions.assertEquals(4, counter);
    }

    @Test
    public void testFormValidationUsernameEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation("", "test", "test@gmail.com");
        });
        String expectedMessage = "Username ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormValidationPasswordEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation("test", "", "test@gmail.com");
        });
        String expectedMessage = "Le mot de passe ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormValidationMailEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation("test", "test", "");
        });
        String expectedMessage = "L'email ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormValidationUsernameNull() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation(null, "test", "test@gmail.com");
        });
        String expectedMessage = "Username ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormValidationPasswordNull() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation("test", null, "test@gmail.com");
        });
        String expectedMessage = "Le mot de passe ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormValidationMailNull() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation("test", "test", null);
        });
        String expectedMessage = "L'email ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormValidationMailExistInBDD() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formValidation("test", "test", "michel@gmail.com");
        });
        String expectedMessage = "Cette email existe déjà !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormUpdateValidationUsernameEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formUpdateValidation("", "test", "test@gmail.com");
        });
        String expectedMessage = "Username ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormUpdateValidationPasswordEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formUpdateValidation("test", "", "test@gmail.com");
        });
        String expectedMessage = "Le mot de passe ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormUpdateValidationMailEmpty() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formUpdateValidation("test", "test", "");
        });
        String expectedMessage = "L'email ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormUpdateValidationUsernameNull() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formUpdateValidation(null, "test", "test@gmail.com");
        });
        String expectedMessage = "Username ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormUpdateValidationPasswordNull() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formUpdateValidation("test", null, "test@gmail.com");
        });
        String expectedMessage = "Le mot de passe ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFormUpdateValidationMailNull() throws IllegalArgumentException {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.formUpdateValidation("test", "test", null);
        });
        String expectedMessage = "L'email ne peut être vide !";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
