package com.tutorial.books.web;


import com.tutorial.books.entity.User;
import com.tutorial.books.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.JdbcSessionProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
public class SessionTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcSessionProperties jdbcSessionProperties;

    @MockBean
    private UserService userService;

    @BeforeEach
    void clearSessionTables() {
        jdbcTemplate.execute("delete from " + jdbcSessionProperties.getTableName());
        jdbcTemplate.execute("delete from " + jdbcSessionProperties.getTableName() + "_ATTRIBUTES");
    }

    @Test
    void testSessionIsCreatedOnLogin() {
        var users = new ArrayList<User>();
        users.add(User.builder().id(1).username("John").birthYear(1995).build());
        users.add(User.builder().id(2).username("Alice").birthYear(1994).build());
        Mockito.when(userService.getAllThatCurrentUserIsAllowedToView()).thenReturn(users);

        var loginPage = testRestTemplate.getForEntity("http://localhost:" + port + "/login", String.class);
        System.out.println(loginPage.getBody());

//
//        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
//                .andExpect(MockMvcResultMatchers.view().name("users/users"))
//                .andExpect(MockMvcResultMatchers.cookie().exists("SESSIONID"));
    }

}
