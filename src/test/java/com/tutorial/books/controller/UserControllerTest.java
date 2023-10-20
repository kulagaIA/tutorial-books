package com.tutorial.books.controller;

import com.tutorial.books.controller.security.WithMockLibraryUser;
import com.tutorial.books.dto.UserCreateDTO;
import com.tutorial.books.entity.Book;
import com.tutorial.books.entity.Role;
import com.tutorial.books.entity.User;
import com.tutorial.books.repository.impl.UserJpaRepository;
import com.tutorial.books.service.BookService;
import com.tutorial.books.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(UserController.class)
@Import(ModelMapper.class)
@WithMockLibraryUser(roles = {Role.Name.ROLE_admin})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserJpaRepository userJpaRepository;

    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    public void testShowUsers() throws Exception {
        var users = new ArrayList<User>();
        users.add(User.builder().id(1).username("John").birthYear(1995).build());
        users.add(User.builder().id(2).username("Alice").birthYear(1994).build());

        Mockito.when(userService.getAllThatCurrentUserIsAllowedToView()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("users", users))
                .andExpect(MockMvcResultMatchers.view().name("users/users"));
    }

    @Test
    @WithAnonymousUser
    public void testShowUsersWithoutLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

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

    @Test
    public void testShowNewUserPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("userCreateDTO", new UserCreateDTO()))
                .andExpect(MockMvcResultMatchers.view().name("users/new"));
    }

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
    @WithMockLibraryUser(id = 2)
    public void testDeleteUserWithoutAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/delete"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testEditUser() throws Exception {
        var user = User.builder().id(1).username("John").birthYear(1995).build();
        Mockito.when(userService.getById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.view().name("users/edit"));
    }

    @Test
    @WithMockLibraryUser(roles = Role.Name.ROLE_user)
    public void testEditUserByOwningUserThatIsNotAdmin() throws Exception {
        var user = User.builder().id(1).username("John").birthYear(1995).build();
        Mockito.when(userService.getById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                .andExpect(MockMvcResultMatchers.view().name("users/edit"));
    }

    @Test
    @WithMockLibraryUser(id = 2, roles = Role.Name.ROLE_user)
    public void testEditUserWithoutAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
                        .with(csrf())
                        .param("birthYear", "1996")
                        .param("name", "Updated Name")
                        .param("username", "aboba"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));

        Mockito.verify(userService).update(userCaptor.capture());

        var capturedUser = userCaptor.getValue();
        Assertions.assertEquals(1, capturedUser.getId());
        Assertions.assertEquals("Updated Name", capturedUser.getName());
        Assertions.assertEquals(1996, capturedUser.getBirthYear());
    }

    @Test
    @WithMockLibraryUser(id = 2)
    public void testUpdateUserWithoutAuthority() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/1/update")
                        .with(csrf())
                        .param("birthYear", "1996")
                        .param("name", "Updated Name"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

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
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("userUpdateDTO", invalidFieldName))
                .andExpect(MockMvcResultMatchers.view().name("users/edit"));

        Mockito.verify(userService, Mockito.never()).update(Mockito.any());
    }

    @Test
    public void testCreateUserWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .with(csrf())
                        .param("name", ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("userCreateDTO", "name"))
                .andExpect(MockMvcResultMatchers.view().name("users/new"));

        Mockito.verify(userService, Mockito.never()).create(Mockito.any());
    }
}