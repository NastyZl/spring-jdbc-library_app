package ru.pvn.libraryApp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pvn.libraryApp.models.Author;
import ru.pvn.libraryApp.models.Book;
import ru.pvn.libraryApp.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;


    private final String selectBooks = "SELECT bks.book_id,\n" +
            "                       bks.book_name, \n" +
            "                       bks.year_of_publishing,\n" +
            "                       gnrs.genre_id, \n" +
            "                       gnrs.genre_name,\n" +
            "                       aut.author_id,\n" +
            "                       aut.first_name as author_first_name,\n" +
            "                       aut.last_name as author_last_name\n" +
            "        FROM BOOK bks\n" +
            "        JOIN AUTHOR aut ON aut.author_id = bks.author_id\n" +
            "        JOIN GENRE gnrs ON gnrs.genre_id = bks.genre_id\n";

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, GenreDaoJdbc genreJdbc, AuthorDaoJdbc authorDaoJdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query(selectBooks +
                        "WHERE bks.book_id = :id",
                Map.of("id", id),
                new BookExtractor());
    }

    @Override
    public void create(Book book) {
        jdbc.update("insert into book (" +
                        " BOOK_NAME,\n" +
                        " YEAR_OF_PUBLISHING,\n" +
                        " GENRE_ID,\n" +
                        " AUTHOR_ID\n" +
                        ")" +
                        "   values ( " +
                        " :name,\n" +
                        " :yearOfPublishing,\n" +
                        " :genreId,\n" +
                        " :authorId\n" +
                        ")",
                Map.of( "name", book.getBookName(),
                        "yearOfPublishing", book.getYearOfPublishing(),
                        "genreId", book.getGenre(),
                        "authorId", book.getAuthor()));
    }

    @Override
    public void update(Book book) {
        jdbc.update("update book  SET" +
                        " BOOK_NAME = :name,\n" +
                        " YEAR_OF_PUBLISHING = :yearOfPublishing,\n" +
                        " GENRE_ID = :genreId,\n" +
                        " AUTHOR_ID = :authorId\n" +
                        "WHERE BOOK_ID = :id",
                Map.of("id", book.getId(),
                        "name", book.getBookName(),
                        "yearOfPublishing", book.getYearOfPublishing(),
                        "genreId", book.getGenre(),
                        "authorId", book.getAuthor()
                ));
    }


    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM book WHERE book_id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(selectBooks +
                " ORDER BY book_id", new BooksExtractor());
    }


    private class BookExtractor implements ResultSetExtractor<Book> {
        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> books = new BooksExtractor().extractData(resultSet);
            if (books.isEmpty()) return null;
            else return books.iterator().next();
        }
    }

    private class BooksExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            Map<Long, Book> books = new HashMap<>();
            while (resultSet.next()) {
                Book book = books.get(resultSet.getLong("book_id"));
                if (book == null) {
                    book = new Book(
                            resultSet.getLong("book_id"),
                            resultSet.getString("book_name"),
                            resultSet.getString("year_of_publishing"),
                            resultSet.getLong("genre_id"),
                            resultSet.getLong("author_id"));
                    books.put(resultSet.getLong("book_id"), book);
                }
            }
            return new ArrayList<>(books.values());
        }
    }
}

