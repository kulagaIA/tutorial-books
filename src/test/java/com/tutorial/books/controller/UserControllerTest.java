package com.tutorial.books.controller;

import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.User;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testShowUsers() throws Exception {
        var users = new ArrayList<User>();
        users.add(new User(1, "John", 1995));
        users.add(new User(2, "Alice", 1994));

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.view().name("users/users"));
    }

    @Test
    public void testShowUser() throws Exception {
        var userId = 1;
        var user = new User(userId, "John", 1995);
        var book1 = new Book(2, "sus", "aa", 123, 1);
        var book2 = new Book(3, "aboba", "burgun", 1234, 5);
        Mockito.when(userService.getById(userId)).thenReturn(user);
        Mockito.when(bookService.getByUserId(userId)).thenReturn(List.of(book1, book2));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.model().attribute("userBooks", List.of(book1, book2)))
                .andExpect(MockMvcResultMatchers.view().name("users/user"));
    }

    @Test
    public void testShowNewUserPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()))
                .andExpect(MockMvcResultMatchers.view().name("users/new"));
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("name", "Alice")
                        .param("birthYear", "1996"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));

        Mockito.verify(userService).create(userCaptor.capture());

        var capturedUser = userCaptor.getValue();
        Assertions.assertEquals("Alice", capturedUser.getName());
        Assertions.assertEquals(1996, capturedUser.getBirthYear());
    }

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

    @Test
    public void testEditUser() throws Exception {
        var user = new User(1, "John", 1995);
        Mockito.when(userService.getById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.view().name("users/edit"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
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

    @Test
    public void testUpdateUserWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
                        .param("id", "1")
                        .param("name", ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                //.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "name"))
                //.andExpect(MockMvcResultMatchers.view().name("users/edit"));

        Mockito.verify(userService, Mockito.never()).update(Mockito.any());
    }

    @Test
    public void testCreateUserWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("name", ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
                //.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("user", "name"))
                //.andExpect(MockMvcResultMatchers.view().name("users/new"));

        Mockito.verify(userService, Mockito.never()).create(Mockito.any());
    }
}