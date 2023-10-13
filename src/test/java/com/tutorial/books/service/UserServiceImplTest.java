package com.tutorial.books.service;

import com.tutorial.books.entity.User;
import com.tutorial.books.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void testGetAll() {
        var user1 = User.builder().id(1).username("John1").birthYear(1991).build();
        var user2 = User.builder().id(2).username("John2").birthYear(1992).build();
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
        var user = User.builder().id(1).username("John1").birthYear(1991).build();

        when(userRepositoryMock.getById(id)).thenReturn(Optional.of(user));

        var result = userService.getById(id);

        assertNotNull(result);
        assertEquals(result, user);
    }

    @Test
    void testGetByIdUserNotExists() {
        var id = 2;
        var user = User.builder().id(1).username("John1").birthYear(1991).build();

        when(userRepositoryMock.getById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getById(id));
    }

    @Test
    void testGetByBookId() {
        var id = 2;
        var user1 = User.builder().id(1).username("John1").birthYear(1991).build();
        var user2 = User.builder().id(2).username("John2").birthYear(1992).build();

        when(userRepositoryMock.getByBookId(id)).thenReturn(List.of(user1, user2));

        var result = userService.getByBookId(id);

        verify(userRepositoryMock, times(1)).getByBookId(id);
        assertNotNull(result);
        assertEquals(result.get(0), user1);
        assertEquals(result.get(1), user2);
    }

    @Test
    void testCreate() {
        var user = User.builder().username("aboba").birthYear(32767).password("aboba").build();

        when(userRepositoryMock.create(user)).thenReturn(user);
        user.setId(new Random().nextInt());

        var result = userService.create(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        var user1 = User.builder().id(1).username("John1").birthYear(1991).build();
        var user2 = User.builder().id(2).username("John2").birthYear(1992).build();
        var user3 = User.builder().id(2).username("John3").birthYear(1993).build();

        when(userRepositoryMock.getWithoutBookByBookId(id)).thenReturn(List.of(user1, user2));

        var result = userService.getWithoutBookByBookId(id);

        verify(userRepositoryMock, times(1)).getWithoutBookByBookId(id);
        assertNotNull(result);
        assertEquals(result.get(0), user1);
        assertEquals(result.get(1), user2);
    }
}
