package com.payMyBuddy.controller;

import com.payMyBuddy.model.Transaction;
import com.payMyBuddy.model.User;
import com.payMyBuddy.model.UserFriends;
import com.payMyBuddy.service.SecurityService;
import com.payMyBuddy.service.TransactionService;
import com.payMyBuddy.service.UserFriendsService;
import com.payMyBuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SecurityService securityService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private UserController userController;

    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser
    public void testTransaction() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        ArrayList<User> listUsers = new ArrayList<>();
        User userLambda = new User();
        userLambda.setId(2);
        userLambda.setEmail("test2@gmail.com");
        userLambda.setUsername("test");
        listUsers.add(userLambda);
        User userExisting = new User();
        userExisting.setId(1);
        userExisting.setEmail("test@gmail.com");
        userExisting.setConnections(listUsers);
        userExisting.setSender(transactions);
        Transaction transaction = new Transaction();
        transaction.setSender(1);
        transaction.setReceiver(2);
        transaction.setDescription("note de frais");
        transaction.setAmount(20);
        transactions.add(transaction);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(transactionService.getTransactions()).thenReturn(transactions);
        when(userService.getUserById(2)).thenReturn(Optional.of(userLambda));
        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction"))
                .andExpect(model().attributeExists("emailFriendsList"))
                .andExpect(model().attributeExists("receivers"))
                .andExpect(model().attributeExists("descriptions"))
                .andExpect(model().attributeExists("amounts"));
    }

    @Test
    @WithMockUser
    public void testPopulateList() throws Exception {
        User userExisting = new User();
        userExisting.setId(1);
        userExisting.setEmail("test@gmail.com");
        User userMail = new User();
        userMail.setId(2);
        userMail.setEmail("test2@gmail.com");
        Transaction transaction = mock(Transaction.class);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(transactionService.addTransaction(userExisting.getId(), userMail.getId(), "note de frais", 20)).thenReturn(transaction);
        mockMvc.perform(post("/transaction")
                .param("email", "test2@gmail.com")
                .param("description", "note de frais")
                .param("amount", String.valueOf(20)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction"));
    }

    @Test
    @WithMockUser
    public void testPopulateListUserNull() throws Exception {
        User userExisting = new User();
        userExisting.setId(1);
        userExisting.setEmail("test@gmail.com");
        User userMail = new User();
        userMail.setId(2);
        userMail.setEmail(null);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(transactionService.addTransaction(userExisting.getId(), userMail.getId(), "note de frais", 20)).thenThrow(new IllegalArgumentException("Veuillez selectionnez une relation valide."));
        mockMvc.perform(post("/transaction")
                        .param("email", "test2@gmail.com")
                        .param("description", "note de frais")
                        .param("amount", String.valueOf(20)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction"))
                .andExpect(flash().attributeExists("error"))
                .andExpect(flash().attribute("error", "Veuillez selectionnez une relation valide."));
    }

    @Test
    @WithMockUser
    public void testPopulateListController() throws Exception {
        User userExisting = new User();
        userExisting.setId(1);
        userExisting.setEmail("test@gmail.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", userExisting);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(userExisting.getEmail());
        when(securityService.isAuthenticated()).thenReturn(true);
        when(securityService.getCurrentUserDetails()).thenReturn(userDetails);
        when(userService.getUserByEmail(userDetails.getUsername())).thenReturn(userExisting);
        when(userService.getUserByEmail(anyString())).thenReturn(mock(User.class));
        mockMvc.perform(post("/transaction")
                        .param("email", "pierre@gmail.com")
                        .param("description", "note de frais")
                        .param("amount", String.valueOf(20)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction"));
    }
}
