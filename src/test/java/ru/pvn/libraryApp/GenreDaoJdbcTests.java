package ru.pvn.libraryApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.pvn.libraryApp.dao.GenreDaoJdbc;
import ru.pvn.libraryApp.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест методов GenreDaoJdbc")
@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTests {

    @Autowired
    private GenreDaoJdbc jdbc;

    @DisplayName("получает жанр по id")
    @Test
    void shouldGetGenreFromDBById() {
        Genre genre = jdbc.getById(1);
        assertThat(genre).hasFieldOrPropertyWithValue("name", "Роман");
    }

    @DisplayName("возвращает новый созданный жанр")
    @Test
    void shouldReturnNewAGenre () {
        Genre genre = new Genre("поэзия");
        jdbc.create(genre);
        Genre genreFromDB = jdbc.getById(4);
        assertThat(genreFromDB).hasFieldOrPropertyWithValue("name", "Фэнтези");
    }

}
