drop table if exists FRIENDS;

drop table if exists GENRE_OF_FILM;

drop table if exists GENRES;

drop table if exists LIKES;

drop table if exists FILMS;

drop table if exists MPA;

drop table if exists USERS;


create table IF NOT EXISTS users
(
    user_id  int auto_increment,
    email    varchar(100) not null,
    login    varchar(50)  not null,
    username varchar(100)  null,
    birthday timestamp    not null,
    constraint USERS_PK
        primary key (user_id)
);

create unique index IF NOT EXISTS USERS_USER_ID_UINDEX
    on users (user_id);

create table IF NOT EXISTS mpa
(
    mpa_id int auto_increment,
    mpa_name   varchar(10) not null,
    constraint MPA_PK
        primary key (mpa_id)
);

create unique index IF NOT EXISTS MPA_MPA_ID_UINDEX
    on mpa (mpa_id);

create table IF NOT EXISTS films
(
    film_id      int auto_increment,
    film_name    varchar(100) not null,
    description  varchar(200) null,
    release_date timestamp    not null,
    duration     int          not null,
    rate         int          null,
    mpa_id       int          not null,
    constraint FILMS_PK
        primary key (film_id),
    constraint MPA_ID_FK
        foreign key (mpa_id) references MPA
);

create unique index IF NOT EXISTS FILMS_FILM_ID_UINDEX
    on films (film_id);

create table IF NOT EXISTS likes
(
    film_id int not null,
    user_id int not null,
    constraint LIKES_PK
        primary key (user_id, film_id),
    constraint LIKES_FILMS_FILM_ID_FK
        foreign key (film_id) references FILMS ON DELETE CASCADE,
    constraint LIKES_USERS_USER_ID_FK
        foreign key (user_id) references USERS
);

create table IF NOT EXISTS friends
(
    user_id   int not null,
    friend_id int not null,
    status boolean default false,
    constraint FRIENDS_PK
        primary key (user_id, friend_id),
    constraint FRIENDS_USERS_USER_ID_FK
        foreign key (user_id) references USERS ON DELETE CASCADE ,
    constraint FRIENDS_USERS_USER_ID_FK_2
        foreign key (friend_id) references USERS
);

create table IF NOT EXISTS genres
(
    genre_id int auto_increment,
    genre_name     varchar(50) not null,
    constraint GENRES_PK
        primary key (genre_id)
);

create unique index IF NOT EXISTS GENRES_GENRE_ID_UINDEX
    on genres (genre_id);

create table IF NOT EXISTS genre_of_film
(
    genre_id int not null,
    film_id  int not null,
    constraint GENRE_OF_FILM_PK
        primary key (genre_id, film_id),
    constraint GENRE_OF_FILM_FILMS_FILM_ID_FK
        foreign key (film_id) references FILMS ON DELETE CASCADE,
    constraint GENRE_OF_FILM_GENRE_GENRE_ID_FK
        foreign key (genre_id) references GENRES ON DELETE CASCADE
);

