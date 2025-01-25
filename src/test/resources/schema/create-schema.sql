create table author
(
    id         bigint auto_increment
        primary key,
    first_name varchar(100) not null,
    last_name  varchar(100) not null
);

create table book
(
    id           bigint auto_increment
        primary key,
    author_id    bigint       not null,
    title        varchar(255) not null,
    release_date date         null,
    constraint book_ibfk_1
        foreign key (author_id) references author (id)
            on delete cascade
);

create index author_id
    on book (author_id);


create table library
(
    id   bigint       not null auto_increment
        primary key,
    name varchar(255) not null
);

create table library_info
(
    id              bigint       not null
        primary key,
    address         varchar(255) not null,
    phone  varchar(15)  null,
    constraint fk_library
        foreign key (id) references library (id)
);

create table library_book
(
    library_id bigint not null,
    book_id    bigint not null,
    primary key (library_id, book_id),
    constraint fk_library_book_book
        foreign key (book_id) references book (id)
            on delete cascade,
    constraint fk_library_book_library
        foreign key (library_id) references library (id)
            on delete cascade
);



