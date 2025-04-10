package com.payMyBuddy.controller;

import com.payMyBuddy.configuration.CustomUserDetailsService;
import com.payMyBuddy.model.User;
import com.payMyBuddy.repository.UserRepository;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecurityService securityService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private UserController userController;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser
    public void testAddUser() throws Exception {
        mockMvc.perform(post("/registration")
                .param("username", "test")
                .param("password", "test")
                .param("email", "test@gmail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser
    public void testAddUserNotComplete() throws Exception {
        String username = "test";
        String password = "test";
        String email = "";
        when(userService.addUser(username, password, email)).thenThrow(new IllegalArgumentException("L'email ne peut être vide !"));
        mockMvc.perform(post("/registration")
                .param("username", username)
                .param("password", password)
                .param("email", email))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registration"))
                .andExpect(flash().attributeExists("error"))
                .andExpect(flash().attribute("error", "L'email ne peut être vide !"));
    }

    @Test
    @WithMockUser
    public void testUpdateUser() throws Exception {
        User userExisting = new User();
        userExisting.setEmail("pierre@gmail.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        User userModified = new User();
        userModified.setUsername("Pierre");
        userModified.setEmail("pierre@gmail.com");
        userModified.setPassword("pierre");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(userService.updateUser(userExisting)).thenReturn(userModified);
        mockMvc.perform(post("/profil")
                        .param("username", userModified.getUsername())
                        .param("password", userModified.getPassword())
                        .param("email", userModified.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profil"));
    }

    @Test
    @WithMockUser
    public void testUpdateUserNotComplete() throws Exception {
        User userExisting = new User();
        userExisting.setEmail("pierre@gmail.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        User userModified = new User();
        userModified.setUsername("Pierre");
        userModified.setEmail("");
        userModified.setPassword("pierre");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(userService.updateUser(userExisting)).thenThrow(new IllegalArgumentException("L'email ne peut être vide !"));
        mockMvc.perform(post("/profil")
                        .param("username", userModified.getUsername())
                        .param("password", userModified.getPassword())
                        .param("email", userModified.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profil"))
                .andExpect(flash().attributeExists("error"))
                .andExpect(flash().attribute("error", "L'email ne peut être vide !"));
    }
}
