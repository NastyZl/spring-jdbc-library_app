package ru.pvn.libraryApp.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.pvn.libraryApp.dao.BookDaoJdbc;
import ru.pvn.libraryApp.dao.GenreDaoJdbc;
import ru.pvn.libraryApp.models.Author;
import ru.pvn.libraryApp.models.Book;
import ru.pvn.libraryApp.models.Genre;

import java.sql.SQLException;
import java.text.ParseException;

@ShellComponent
public class ShellCommands {

    private final AuthorDaoJdbc jdbcAuthor;
    private final GenreDaoJdbc jdbcGenreDao;
    private final BookDaoJdbc jdbcBookDao;

    public ShellCommands(AuthorDaoJdbc jdbcAuthor, GenreDaoJdbc jdbcGenreDao, BookDaoJdbc jdbcBookDao) {
        this.jdbcAuthor = jdbcAuthor;
        this.jdbcGenreDao = jdbcGenreDao;
        this.jdbcBookDao = jdbcBookDao;
    }

    @ShellMethod(value = "run H2 console", key = {"console"})
    public String runConsoleH2() throws SQLException {
        Console.main();
        return "Консоль H2 запущена";
    }

    @ShellMethod(value = "add Author to DB", key = {"add-author"})
    public String addAuthor(@ShellOption String first_name,
                            @ShellOption String last_name) throws SQLException, ParseException {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Author author = null;
        author = new Author(first_name, last_name);
        jdbcAuthor.create(author);
        return String.format("Автор %s добавлен", author);
    }

    @ShellMethod(value = "add Genre to DB", key = {"add-genre"})
    public String addAuthor(@ShellOption String name) throws SQLException {
        Genre genre = new Genre(name);
        jdbcGenreDao.create(genre);
        return String.format("Жанр %s добавлен", name);
    }

    @ShellMethod(value = "delete book from DB", key = {"delete-book"})
    public String deleteBook(@ShellOption long id) throws SQLException {
        jdbcBookDao.deleteById(id);
        return String.format("Книга id=%s удалена", id);
    }

    @ShellMethod(value = "get book from DB", key = {"get-book"})
    public String getBook(@ShellOption long id) throws SQLException {
        Book book = jdbcBookDao.getById(id);
        return book.toString();
    }


    @ShellMethod(value = "add book to DB", key = {"add-book"})
    public String addBook(@ShellOption String bookName,
                          @ShellOption String yearOfPublishing,
                          @ShellOption Long genre,
                          @ShellOption Long author) throws SQLException {
        Book book = new Book();
        book.setBookName(bookName);
        book.setYearOfPublishing(yearOfPublishing);
        book.setGenre(genre);
        book.setAuthor(author);
        jdbcBookDao.create(book);
        return "Книга " + book.toString() + "добавлена!";
    }

    @ShellMethod(value = "update book in DB", key = {"update-book"})
    public String updateBookById(@ShellOption String id,
                                 @ShellOption String bookName,
                                 @ShellOption String yearOfPublishing,
                                 @ShellOption Long genre,
                                 @ShellOption Long author) throws SQLException {

        Book bookForUpdate = new Book();
        bookForUpdate.setId(Long.parseLong(id));
        bookForUpdate.setBookName(bookName);
        bookForUpdate.setYearOfPublishing(yearOfPublishing);
        bookForUpdate.setGenre(genre);
        bookForUpdate.setAuthor(genre);
        jdbcBookDao.update(bookForUpdate);
        return "Книга " + bookForUpdate.toString() + "обновлена!";
    }
}






