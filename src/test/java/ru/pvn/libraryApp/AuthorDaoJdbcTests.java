package ru.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.pvn.libraryApp.models.Author;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест методов AuthorDaoJdbc")
@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTests {

    @Autowired
    private AuthorDaoJdbc jdbc;

    @DisplayName("получает книгу по id")
    @Test
    void shouldGetAuthorFromDBById() {
        Author author = jdbc.getById(1);
        assertThat(author).hasFieldOrPropertyWithValue("lastName", "Толстой");
    }

    @DisplayName("получает книгу по last-name")
    @Test
    void shouldGetAuthorFromDBByFio() {
        Author author = jdbc.getByLastName("Достоевский");
        assertThat(author).hasFieldOrPropertyWithValue("firstName", "Федор");
    }

    @DisplayName("возвращает новую созданную книгу")
    @Test
    void shouldReturnNewAuthor () {
//        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        Author author = new Author("Анастасия", "ЯЯ");
        jdbc.create(author);
        Author authorFromDB = jdbc.getByLastName("ЯЯ");
        assertThat(authorFromDB).hasFieldOrPropertyWithValue("lastName", "ЯЯ");
    }

}
