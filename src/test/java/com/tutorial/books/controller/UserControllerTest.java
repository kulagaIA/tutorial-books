package com.tutorial.books.controller;

import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.User;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import com.tutorial.books.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @WithMockUser
    @Test
    public void testShowUsers() throws Exception {
        var users = new ArrayList<User>();
        users.add(User.builder().id(1).username("John").birthYear(1995).build());
        users.add(User.builder().id(2).username("Alice").birthYear(1994).build());

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.view().name("users/users"));
    }

    @Test
    public void testShowUsersWithoutLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @WithMockUser
    @Test
    public void testShowUser() throws Exception {
        var userId = 1;
        var user = User.builder().id(1).username("John").birthYear(1995).build();
        var book1 =
                Book.builder().id(2).name("sus").author("aa").publishYear(123).quantityAvailable(1).build();
        var book2 =
                Book.builder().id(3).name("aboba").author("burgun").publishYear(1234).quantityAvailable(5).build();
        Mockito.when(userService.getById(userId)).thenReturn(user);
        Mockito.when(bookService.getByUserId(userId)).thenReturn(List.of(book1, book2));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.model().attribute("userBooks", List.of(book1, book2)))
                .andExpect(MockMvcResultMatchers.view().name("users/user"));
    }

    @WithMockUser
    @Test
    public void testShowNewUserPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()))
                .andExpect(MockMvcResultMatchers.view().name("users/new"));
    }

    @WithMockUser
    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .with(csrf())
                        .param("name", "Alice")
                        .param("birthYear", "1996")
                        .param("username", "ABOBA")
                        .param("password", "definitelySafePassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));

        Mockito.verify(userService).create(userCaptor.capture());

        var capturedUser = userCaptor.getValue();
        Assertions.assertEquals("Alice", capturedUser.getName());
        Assertions.assertEquals(1996, capturedUser.getBirthYear());
        Assertions.assertEquals("ABOBA", capturedUser.getUsername());
        Assertions.assertEquals("definitelySafePassword", capturedUser.getPassword());
    }

    @WithMockUser(authorities = {Constants.ADMIN})
    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));

        var idCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(userService).delete(idCaptor.capture());

        var capturedId = idCaptor.getValue();
        Assertions.assertEquals(1, capturedId.intValue());
    }

    @WithMockUser
    @Test
    public void testDeleteUserWithoutAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/delete"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(authorities = {Constants.ADMIN})
    @Test
    public void testEditUser() throws Exception {
        var user = User.builder().id(1).username("John").birthYear(1995).build();
        Mockito.when(userService.getById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.view().name("users/edit"));
    }

    @WithMockUser
    @Test
    public void testEditUserWithoutAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @WithMockUser(authorities = {Constants.ADMIN})
    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
                        .with(csrf())
                        .param("birthYear", "1996")
                        .param("name", "Updated Name"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));

        Mockito.verify(userService).update(userCaptor.capture());

        var capturedUser = userCaptor.getValue();
        Assertions.assertEquals(1, capturedUser.getId());
        Assertions.assertEquals("Updated Name", capturedUser.getName());
        Assertions.assertEquals(1996, capturedUser.getBirthYear());
    }

    @WithMockUser
    @Test
    public void testUpdateUserWithoutAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
                        .with(csrf())
                        .param("birthYear", "1996")
                        .param("name", "Updated Name"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @WithMockUser(authorities = {Constants.ADMIN})
    @Test
    public void testUpdateUserWithInvalidInput() throws Exception {
        var invalidFieldName = "birthYear";
        var invalidFieldValue = "-32767";

        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
                        .with(csrf())
                        .param("id", "1")
                        .param(invalidFieldName, invalidFieldValue)
                        .param("name", "aboba"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", invalidFieldName))
                .andExpect(MockMvcResultMatchers.view().name("users/edit"));

        Mockito.verify(userService, Mockito.never()).update(Mockito.any());
    }

    @WithMockUser
    @Test
    public void testCreateUserWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .with(csrf())
                        .param("name", ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "name"))
                .andExpect(MockMvcResultMatchers.view().name("users/new"));

        Mockito.verify(userService, Mockito.never()).create(Mockito.any());
    }
}