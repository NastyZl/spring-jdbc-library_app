package ru.pvn.libraryApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    private long id;

    private String bookName;

    private String yearOfPublishing;

    private long genre;

    private long author;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", yearOfPublishing='" + yearOfPublishing + '\'' +
                ", genre=" + genre +
                ", author=" + author +
                '}';
    }
}
