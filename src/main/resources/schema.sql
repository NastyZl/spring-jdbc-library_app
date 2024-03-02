create table Genre
(
    genre_id   int generated by default as identity primary key,
    genre_name varchar(50) not null
);

create table Author
(
    author_id  int generated by default as identity primary key,
    first_name varchar(50) not null,
    last_name  varchar(50) not null
);

create table Book
(
    book_id            int generated by default as identity primary key,
    book_name          varchar(100) not null,
    year_of_publishing int check (year_of_publishing > 0 AND year_of_publishing <= extract(year from now())),
    genre_id           int          not null references Genre (genre_id),
    author_id          int          not null references Author (author_id)
);