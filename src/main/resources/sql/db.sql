DROP DATABASE IF EXISTS film_rating;
CREATE DATABASE film_rating;

use film_rating;

create table account
(
    id            int auto_increment
        primary key,
    username      varchar(50)            not null,
    role          enum ('ADMIN', 'USER') null,
    password      varchar(50)            not null,
    full_name     varchar(50)            null,
    email         varchar(50)            not null,
    date_of_birth date                   null,
    constraint account_username_uindex
        unique (username)
);

create table film
(
    id          int auto_increment
        primary key,
    title       varchar(255) not null,
    image       varchar(255) null,
    description varchar(255) null
);

create table genre
(
    id   int auto_increment
        primary key,
    name varchar(50) not null,
    constraint genre_name_uindex
        unique (name)
);

create table film_genre
(
    film_id  int null,
    genre_id int null,
    constraint film_genre_film_id_fk
        foreign key (film_id) references film (id),
    constraint film_genre_genre_id_fk
        foreign key (genre_id) references genre (id)
);

create table review
(
    id         int auto_increment
        primary key,
    content    varchar(255) not null,
    post_date  date         null,
    rating     float        not null,
    account_id int          not null,
    film_id    int          not null,
    constraint review_account_id_fk
        foreign key (account_id) references account (id),
    constraint review_film_id_fk
        foreign key (film_id) references film (id)
);

