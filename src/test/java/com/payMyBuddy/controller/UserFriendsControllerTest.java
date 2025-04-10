package com.payMyBuddy.controller;

import com.payMyBuddy.model.User;
import com.payMyBuddy.model.UserFriends;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.UserFriendsService;
import com.payMyBuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserFriendsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecurityService securityService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserFriendsService userFriendsService;

    @Autowired
    private UserController userController;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddUserFriends() throws Exception {
        User userExisting = new User();
        userExisting.setId(1);
        userExisting.setEmail("test@gmail.com");
        User userMail = new User();
        userMail.setId(2);
        userMail.setEmail("test2@gmail.com");
        UserFriends userFriends = new UserFriends();
        userFriends.setUserId(userExisting.getId());
        userFriends.setUserFriends(userMail.getId());
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(userFriendsService.addUserFriends(userExisting.getId(), userMail.getId())).thenReturn(userFriends);
        mockMvc.perform(post("/relationship")
                        .param("email", userMail.getEmail()))
                .andExpect(status().is3xxRedirection());
                //.andExpect(redirectedUrl("/relationship"));
    }
}
