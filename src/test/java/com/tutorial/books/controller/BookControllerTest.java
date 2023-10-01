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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BookService bookService;

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @WithMockUser
    @Test
    public void testShowBooks() throws Exception {
        var books = new ArrayList<Book>();
        Book.builder().id(1).name("John").author("Sus").publishYear(1995).quantityAvailable(2).build();
        books.add(
                Book.builder().id(1).name("John").author("Sus").publishYear(1995).quantityAvailable(2).build());
        books.add(
                Book.builder().id(2).name("Alice").author("Aboba").publishYear(1994).quantityAvailable(5).build()
        );

        Mockito.when(bookService.getAll()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("books", books))
                .andExpect(MockMvcResultMatchers.view().name("books/books"));
    }

    @WithMockUser
    @Test
    public void testShowBook() throws Exception {
        var bookId = 32767;
        var book = Book.builder().id(bookId).name("sus").author("aa").quantityAvailable(1).build();
        var user1 = User.builder().id(1).username("John1").birthYear(1991).build();
        var user2 = User.builder().id(2).username("John2").birthYear(1992).build();
        var usersWithBook = List.of(user1, user2);
        var user3 = User.builder().id(3).username("John3").birthYear(1993).build();
        var user4 = User.builder().id(4).username("John4").birthYear(1994).build();
        var usersWithoutBook = List.of(user3, user4);

        Mockito.when(bookService.getById(bookId)).thenReturn(book);
        Mockito.when(userService.getByBookId(bookId)).thenReturn(usersWithBook);
        Mockito.when(userService.getWithoutBookByBookId(bookId)).thenReturn(usersWithoutBook);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("user", new User()))
                .andExpect(MockMvcResultMatchers.model().attribute("book", book))
                .andExpect(MockMvcResultMatchers.model().attribute("usersWithoutBook", usersWithoutBook))
                .andExpect(MockMvcResultMatchers.model().attribute("bookUsers", usersWithBook))
                .andExpect(MockMvcResultMatchers.view().name("books/book"));
    }

    @WithMockUser
    @Test
    public void testShowNewBookPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("book", new Book()))
                .andExpect(MockMvcResultMatchers.view().name("books/new"));
    }

    @WithMockUser
    @Test
    public void testCreateBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books/create")
                        .with(csrf())
                        .param("name", "Alice")
                        .param("author", "Aboba")
                        .param("quantityAvailable", "32767")
                        .param("publishYear", "1996"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/books"));

        verify(bookService).create(bookCaptor.capture());

        var capturedBook = bookCaptor.getValue();
        Assertions.assertEquals("Alice", capturedBook.getName());
        Assertions.assertEquals(1996, capturedBook.getPublishYear());
        Assertions.assertEquals("Aboba", capturedBook.getAuthor());
        Assertions.assertEquals(32767, capturedBook.getQuantityAvailable());
    }

    @WithMockUser
    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/books"));

        var idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(bookService).delete(idCaptor.capture());

        var capturedId = idCaptor.getValue();
        Assertions.assertEquals(1, capturedId.intValue());
    }

    @WithMockUser
    @Test
    public void testEditBook() throws Exception {
        var book = Book.builder().id(1).name("John").author("Sus").publishYear(1995).quantityAvailable(2).build();
        Mockito.when(bookService.getById(1)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/1/edit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("book", book))
                .andExpect(MockMvcResultMatchers.view().name("books/edit"));
    }

    @WithMockUser
    @Test
    public void testUpdateBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books/1/update")
                        .with(csrf())
                        .param("name", "Alice")
                        .param("author", "Updated Aboba")
                        .param("quantityAvailable", "32767")
                        .param("publishYear", "1996"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/books"));

        verify(bookService).update(bookCaptor.capture());

        var capturedBook = bookCaptor.getValue();
        Assertions.assertEquals("Updated Aboba", capturedBook.getAuthor());
        Assertions.assertEquals(1, capturedBook.getId());
        Assertions.assertEquals("Alice", capturedBook.getName());
        Assertions.assertEquals(1996, capturedBook.getPublishYear());
        Assertions.assertEquals(32767, capturedBook.getQuantityAvailable());
    }

    @WithMockUser
    @Test
    public void testUpdateBookWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books/1/update")
                        .with(csrf())
                        .param("id", "1")
                        .param("name", ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("book", "name"))
        .andExpect(MockMvcResultMatchers.view().name("books/edit"));

        verify(userService, Mockito.never()).update(Mockito.any());
    }

    @WithMockUser
    @Test
    public void testCreateBookWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books/create")
                        .with(csrf())
                        .param("name", ""))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("book", "name"))
        .andExpect(MockMvcResultMatchers.view().name("books/new"));

        verify(userService, Mockito.never()).create(Mockito.any());
    }

    @WithMockUser
    @Test
    public void testGiveToUser() throws Exception {
        Integer userId = 123;
        Integer bookId = 32767;

        mockMvc.perform(MockMvcRequestBuilders.post("/books/" + bookId + "/give")
                        .with(csrf())
                        .param("id", userId.toString()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books/" + bookId));

        verify(bookService).giveToUser(bookId, userId);
    }

    @WithMockUser
    @Test
    public void testReturnFromUser() throws Exception {
        Integer userId = 123;
        Integer bookId = 32767;

        mockMvc.perform(MockMvcRequestBuilders.post("/books/" + bookId + "/return")
                        .with(csrf())
                        .param("id", userId.toString()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books/" + bookId));

        verify(bookService).returnFromUser(bookId, userId);
    }
}