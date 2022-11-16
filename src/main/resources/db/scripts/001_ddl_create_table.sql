CREATE TABLE IF NOT EXISTS person
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(2000),
    password VARCHAR(2000)
);