package ru.pvn.libraryApp.dao;

import ru.pvn.libraryApp.models.Author;

public interface AuthorDao {
    Author getById(long id);
    Author getByLastName(String lastName);
    void create(Author author);
}
