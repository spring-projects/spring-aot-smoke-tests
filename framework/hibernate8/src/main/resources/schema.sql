create table author
(
    id   bigint auto_increment primary key,
    name varchar not null
);

create table book
(
    id     bigint auto_increment primary key,
    author bigint  not null references author (id),
    title  varchar not null
);

create table book_authors (
    book_id bigint,
    authors_id bigint
)
