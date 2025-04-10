package com.payMyBuddy.controller;

import com.payMyBuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser
    public void testGetRegistration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    @WithMockUser
    public void testGetRelationship() throws Exception {
        mockMvc.perform(get("/relationship"))
                .andExpect(status().isOk())
                .andExpect(view().name("relationship"));
    }

    @Test
    @WithMockUser
    public void testGetLogin() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("test@gmail.com")
                .password("test")
                .roles("USER")
                .build();
        Authentication authentication = new TestingAuthenticationToken(userDetails, null, "ROLE_USER");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        com.payMyBuddy.model.User user = new com.payMyBuddy.model.User();
        user.setEmail("test@gmail.com");
        when(userService.getUserByEmail("test@gmail.com")).thenReturn(user);
        mockMvc.perform(get("/login").with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"));
    }

    @Test
    @WithMockUser
    public void testGetProfil() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("test@gmail.com")
                .password("test")
                .roles("USER")
                .build();
        Authentication authentication = new TestingAuthenticationToken(userDetails, null, "ROLE_USER");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        com.payMyBuddy.model.User user = new com.payMyBuddy.model.User();
        user.setEmail("test@gmail.com");
        when(userService.getUserByEmail("test@gmail.com")).thenReturn(user);
        mockMvc.perform(get("/profil").with(authentication(authentication)))
                .andExpect(status().isOk())
                .andExpect(view().name("profil"));
    }

    @Test
    @WithMockUser
    public void testGetLoginNotFound() throws Exception {
        UserDetails userDetails = null;
        Authentication authentication = new TestingAuthenticationToken(userDetails, null, "ROLE_USER");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
