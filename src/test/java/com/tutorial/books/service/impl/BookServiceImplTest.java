package com.tutorial.books.service.impl;

import com.tutorial.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("junit")
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepositoryMock;

//    @Test
//    void testGetAll() {
//        var user1 = new User(1, "lol", 32767);
//        var user2 = new User(2, "lal", 1991);
//        when(userRepositoryMock.getAll()).thenReturn(List.of(user1, user2));
//
//        var result = userService.getAll();
//
//        verify(userRepositoryMock, times(1)).getAll();
//        assertNotNull(result);
//        assertEquals(result.get(0), user1);
//        assertEquals(result.get(1), user2);
//    }
//
//    @Test
//    void testGetById() {
//        var id = 2;
//        var user = new User(id, "lal", 1991);
//
//        when(userRepositoryMock.getById(id)).thenReturn(Optional.of(user));
//
//        var result = userService.getById(id);
//
//        assertNotNull(result);
//        assertEquals(result, user);
//    }
//
//    @Test
//    void testGetByIdUserNotExists() {
//        var id = 2;
//        var user = new User(id, "lal", 1991);
//
//        when(userRepositoryMock.getById(id)).thenReturn(Optional.empty());
//
//        assertThrows(NoSuchElementException.class, () -> userService.getById(id));
//    }
}
