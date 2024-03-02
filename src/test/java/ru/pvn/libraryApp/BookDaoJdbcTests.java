package ru.pvn.libraryApp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.pvn.libraryApp.dao.BookDaoJdbc;
import ru.pvn.libraryApp.dao.GenreDaoJdbc;
import ru.pvn.libraryApp.models.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест методов BookDaoJdbc")
@JdbcTest
@Import({AuthorDaoJdbc.class, BookDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTests {

    @Autowired
    private AuthorDaoJdbc authorJdbc;
    @Autowired
    private BookDaoJdbc bookJdbc;
    @Autowired
    private GenreDaoJdbc genreJdbc;

    @DisplayName("получить книгу по id")
    @Test
    void shouldGetBookFromDBById() {
        Book book = bookJdbc.getById(1);
        assertThat(book).hasFieldOrPropertyWithValue("bookName", "Война и мир");
    }

    @DisplayName("добавить книгу")
    @Test
    void shouldCreateBook() {
        Book book = bookJdbc.getById(1);
        book.setBookName("Сборник типа русских авторов йоу!");
        bookJdbc.create(book);
        Assertions.assertThat(bookJdbc.getById(11)).hasFieldOrPropertyWithValue("bookName", "Сборник типа русских авторов йоу!");
    }

    @DisplayName("обновить книгу")
    @Test
    void shouldUpdateBook() {
        Book book = bookJdbc.getById(2);
        book.setBookName("Ужасы говнокода!");
        bookJdbc.update(book);
        book = bookJdbc.getById(2);
        assertThat(book).hasFieldOrPropertyWithValue("bookName", "Ужасы говнокода!");
    }

    @DisplayName("удалить книгу")
    @Test
    void shouldDeleteBook() {
        bookJdbc.deleteById(1);
        Assertions.assertThat(bookJdbc.getById(1)).isNull();
    }

    @DisplayName("получить книги")
    @Test
    void shouldGetAllBook() {
        List<Book> books = bookJdbc.getAll();
        assertThat(books).hasSizeGreaterThanOrEqualTo(2);
    }


}
