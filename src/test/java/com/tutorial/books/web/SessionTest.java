//package com.tutorial.books.web;
//
//
//import com.tutorial.books.entity.Role;
//import com.tutorial.books.entity.User;
//import com.tutorial.books.security.LibraryUserDetails;
//import com.tutorial.books.service.UserService;
//import com.tutorial.books.service.impl.UserDetailsServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.session.JdbcSessionProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.Set;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("junit")
//public class SessionTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Autowired
//    protected JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private JdbcSessionProperties jdbcSessionProperties;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private UserDetailsServiceImpl userDetailsService;
//
//    @BeforeEach
//    void clearSessionTables() {
//        jdbcTemplate.execute("delete from " + jdbcSessionProperties.getTableName());
//        jdbcTemplate.execute("delete from " + jdbcSessionProperties.getTableName() + "_ATTRIBUTES");
//    }
//
//    @Test
//    void testSessionIsCreatedOnLogin() {
//        var users = new ArrayList<User>();
//        var username = "John";
//        var password = "{noop}pass";
//        var userJohn = User.builder()
//                .id(1)
//                .username(username)
//                .password(password)
//                .roles(Set.of(Role.builder().name(Role.Name.ROLE_admin).build()))
//                .build();
//        users.add(userJohn);
//        users.add(User.builder().id(2).username("Alice").birthYear(1994).build());
//        Mockito.when(userService.getAllThatCurrentUserIsAllowedToView()).thenReturn(users);
//
//        Mockito.when(userDetailsService.loadUserByUsername("John")).thenReturn(LibraryUserDetails.buildFromUser(userJohn));
//
//        var loginPage = testRestTemplate.getForEntity("http://localhost:" + port + "/login", String.class).getBody();
//        assert loginPage != null;
//        var csrf = getCSRFFromDefaultSpringLoginPage(loginPage);
//
//
//        var afterLoginPage = testRestTemplate.postForEntity(
//                "http://localhost:" + port + "/login",
//                makeLoginPostBody(username, password, csrf),
//                String.class);
//
//
//        System.out.println(afterLoginPage);
//
//
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
////                .andExpect(MockMvcResultMatchers.view().name("users/users"))
////                .andExpect(MockMvcResultMatchers.cookie().exists("SESSIONID"));
//    }
//
//    private String makeLoginPostBody(String username, String password, String csrf) {
//        return "username=" + username +
//                "&password=" + password +
//                "&_csrf=" + csrf;
//    }
//
//    private String getCSRFFromDefaultSpringLoginPage(String loginPage) {
//        var csrfStart = loginPage.substring(loginPage.indexOf("name=\"_csrf\" type=\"hidden\" value=\"") + 34);
//        return csrfStart.substring(0, csrfStart.indexOf("\""));
//    }
//
//}
