CREATE TABLE products
(
    id           BIGSERIAL PRIMARY KEY,
    category     TEXT    NOT NULL,
    name         TEXT    NOT NULL,
    description  TEXT    NOT NULL,
    price        BIGINT  NOT NULL CHECK ( price >= 0 ),
    size         BIGINT  NOT NULL,
    manufacturer TEXT    NOT NULL,
    photo        TEXT    NOT NULL,
    removed      BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    TEXT    NOT NULL UNIQUE,
    role     TEXT    NOT NULL,
    password TEXT    NOT NULL,
    removed  BOOLEAN NOT NULL DEFAULT FALSE
);


CREATE TABLE purchases
(
    id               BIGSERIAL PRIMARY KEY,
    product_id       BIGINT                  NOT NULL REFERENCES products,
    product_category TEXT                    NOT NULL,
    product_name     TEXT                    NOT NULL,
    product_price    BIGINT                  NOT NULL,
    qty              BIGINT                  NOT NULL,
    user_id          BIGINT REFERENCES users NOT NULL
);

CREATE TABLE bonuses
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users,
    bonus   BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE reviews
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL REFERENCES users,
    product_id BIGINT NOT NULL REFERENCES products,
    comment    TEXT   NOT NULL
);

