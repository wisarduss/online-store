CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR NOT NULL,
    surname     VARCHAR NOT NUll,
    email       VARCHAR NOT NULL UNIQUE,
    password    VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS items
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR NOT NULL,
    description     VARCHAR NOT NULL,
    photo_url       VARCHAR NOT NULL,
    price           INTEGER NOT NULL,
    status          VARCHAR NOT NULL,
    total_count     INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS item_categories
(
   item_id  INTEGER NOT NULL,
   cat_id   INTEGER NOT NULL,
   PRIMARY KEY (item_id, cat_id),
   FOREIGN KEY (item_id) REFERENCES items(id),
   FOREIGN KEY (cat_id) REFERENCES categories(id)

);


