CREATE TABLE person
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(2000),
    password VARCHAR(2000)
);

INSERT INTO person (login, password)
VALUES ('parsentev', '123');
INSERT INTO person (login, password)
VALUES ('ban', '123');
INSERT INTO person (login, password)
VALUES ('ivan', '123');