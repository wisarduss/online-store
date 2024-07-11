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

CREATE TABLE IF NOT EXISTS fk_categories_to_items
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR NOT NULL,
    item_id     INTEGER NOT NULL,
    CONSTRAINT fk_categories_to_items FOREIGN KEY (item_id) REFERENCES items (id),
    UNIQUE(id)
);
