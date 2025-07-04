create table book
(
    id     serial PRIMARY KEY,
    author integer NOT NULL REFERENCES author (id),
    title  varchar NOT NULL
);
