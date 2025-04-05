CREATE TABLE genre
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

insert into genre (name) values ('default');