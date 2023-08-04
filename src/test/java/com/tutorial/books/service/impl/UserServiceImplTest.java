package com.tutorial.books.service.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import com.tutorial.books.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("junit")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepositoryMock;

    @Test
    void testGetAll() {
        var user1 = new User(1, "lol", 32767);
        var user2 = new User(2, "lal", 1991);
        when(userRepositoryMock.getAll()).thenReturn(List.of(user1, user2));

        var result = userService.getAll();

        verify(userRepositoryMock, times(1)).getAll();
        assertNotNull(result);
        assertEquals(result.get(0), user1);
        assertEquals(result.get(1), user2);
    }

    @Test
    void testGetById() {
        var id = 2;
        var user = new User(id, "lal", 1991);

        when(userRepositoryMock.getById(id)).thenReturn(Optional.of(user));

        var result = userService.getById(id);

        assertNotNull(result);
        assertEquals(result, user);
    }

    @Test
    void testGetByIdUserNotExists() {
        var id = 2;
        var user = new User(id, "lal", 1991);

        when(userRepositoryMock.getById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getById(id));
    }

    @Test
    void testGetByBookId() {
        var id = 2;
        var user1 = new User(1, "lol", 32767);
        var user2 = new User(2, "lal", 1991);

        when(userRepositoryMock.getByBookId(id)).thenReturn(List.of(user1, user2));

        var result = userService.getByBookId(id);

        verify(userRepositoryMock, times(1)).getByBookId(id);
        assertNotNull(result);
        assertEquals(result.get(0), user1);
        assertEquals(result.get(1), user2);
    }

    @Test
    void testCreate() {
        var user = User.builder().name("aboba").birthYear(32767).build();

        when(userRepositoryMock.create(user)).thenReturn(user);
        user.setId(new Random().nextInt());

        var result = userService.create(user);

        assertEquals(user, result);
    }

    @Test
    void testDelete() {
        userService.delete(1);

        verify(userRepositoryMock, times(1)).delete(1);
    }

    @Test
    void testUpdate() {
        var user = new User();
        userService.update(user);

        verify(userRepositoryMock, times(1)).update(user);
    }

    @Test
    void testGetWithoutBookByBookId() {
        var id = 2;
        var user1 = new User(1, "lol", 32767);
        var user2 = new User(2, "lal", 1991);
        var user3 = new User(2, "lil", 1985);

        when(userRepositoryMock.getWithoutBookByBookId(id)).thenReturn(List.of(user1, user2));

        var result = userService.getWithoutBookByBookId(id);

        verify(userRepositoryMock, times(1)).getWithoutBookByBookId(id);
        assertNotNull(result);
        assertEquals(result.get(0), user1);
        assertEquals(result.get(1), user2);
    }
}
