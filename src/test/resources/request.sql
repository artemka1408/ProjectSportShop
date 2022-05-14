INSERT INTO users (login, role, password)
VALUES ('andrey','user', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('igor','user', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('vladimir','user', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('vitaliy','user', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('admin','admin', 'top-secret');

SELECT id, login FROM users
ORDER BY id
    LIMIT 3 OFFSET 0;

SELECT id, login FROM users
ORDER BY id
    LIMIT 3 OFFSET 3;

UPDATE users SET password = 'top-secret3'
WHERE id = 4
    RETURNING id, role, login;

UPDATE users SET removed = TRUE
WHERE id = 1
    RETURNING id, role, login;

UPDATE users SET removed = FALSE
WHERE id = 1
    RETURNING id, role, login;


