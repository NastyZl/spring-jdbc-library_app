package ru.pvn.libraryApp.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pvn.libraryApp.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public Author getById(long id) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM AUTHOR WHERE AUTHOR_ID = :id",
                Map.of("id", id),
                new AuthorMapper());
    }

    @Override
    public Author getByLastName(String lastName) {
        final Map<String,Object> params = new HashMap<>(1);
        params.put("name", lastName);
        return jdbc.queryForObject("SELECT * FROM AUTHOR WHERE LAST_NAME = :name",
                Map.of("name", lastName),
                new AuthorMapper());
    }
//insert into Author (first_name, last_name) values ('Лев', 'Толстой');
    @Override
    public void create(Author author) {
        jdbc.update("insert into author (" +
                        "    first_name,\n" +
                        "    last_name\n" +
                        ")"+
                        "   values ( " +
                        "      :firstName,\n" +
                        "      :lastName\n" +
                        ")",
                Map.of("firstName", author.getFirstName(),
                       "lastName", author.getLastName()));
        }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("author_id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
        }
    }
}


