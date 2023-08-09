package com.tutorial.books.service;

import com.tutorial.books.entity.Book;
import com.tutorial.books.repository.BookRepository;
import com.tutorial.books.service.impl.BookServiceImpl;
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
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookRepository bookRepositoryMock;

    @Test
    void testGetAll() {
        var book1 = new Book(1, "lol", "abobus", 32767, 10);
        var book2 = new Book(1, "lol", "abobus", 32767, 10);

        when(bookRepositoryMock.getAll()).thenReturn(List.of(book1, book2));

        var result = bookService.getAll();

        verify(bookRepositoryMock, times(1)).getAll();
        assertNotNull(result);
        assertEquals(result.get(0), book1);
        assertEquals(result.get(1), book2);
    }

    @Test
    void testGetById() {
        var id = 2;
        var book = new Book(1, "lol", "abobus", 32767, 10);

        when(bookRepositoryMock.getById(id)).thenReturn(Optional.of(book));

        var result = bookService.getById(id);

        assertNotNull(result);
        assertEquals(result, book);
    }

    @Test
    void testGetByIdUserNotExists() {
        var id = 2;

        when(bookRepositoryMock.getById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookService.getById(id));
    }

    @Test
    void testGetByUserId() {
        var id = 2;
        var book1 = new Book(id, "lol", "abobus", 32767, 10);
        var book2 = new Book(id, "lol", "abobus", 32767, 10);

        when(bookRepositoryMock.getByUserId(id)).thenReturn(List.of(book1, book2));

        var result = bookService.getByUserId(id);

        verify(bookRepositoryMock, times(1)).getByUserId(id);
        assertNotNull(result);
        assertEquals(result.get(0), book1);
        assertEquals(result.get(1), book2);
    }

    @Test
    void testCreate() {
        var book = Book.builder()
                .name("aboba")
                .publishYear(32767)
                .author("sus")
                .quantityAvailable(1)
                .build();

        when(bookRepositoryMock.create(book)).thenReturn(book);
        book.setId(new Random().nextInt());

        var result = bookService.create(book);

        assertEquals(book, result);
    }

    @Test
    void testDelete() {
        bookService.delete(1);

        verify(bookRepositoryMock, times(1)).delete(1);
    }

    @Test
    void testUpdate() {
        var book = new Book();
        bookService.update(book);

        verify(bookRepositoryMock, times(1)).update(book);
    }

    @Test
    void testGiveToUser() {
        var bookId = 2;
        var userId = 32767;
        bookService.giveToUser(bookId, userId);

        verify(bookRepositoryMock, times(1)).bindToUser(bookId, userId);
        verify(bookRepositoryMock, times(1)).decreaseQuantityAvailable(bookId);
    }

    @Test
    void testReturnFromUser() {
        var bookId = 2;
        var userId = 32767;
        bookService.returnFromUser(bookId, userId);

        verify(bookRepositoryMock, times(1)).unbindFromUser(bookId, userId);
        verify(bookRepositoryMock, times(1)).increaseQuantityAvailable(bookId);
    }
}
