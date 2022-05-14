INSERT INTO users (login, role, password)
VALUES ('andrey','USER', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('igor','USER', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('vladimir','USER', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('vitaliy','USER', '$2a$10$AMzhFC74gCxYgvSvPQuBO.UEXvzodMe/Yso6fooawEyP9Lc2F9h0a');

INSERT INTO users (login, role, password)
VALUES ('admin','ADMIN', '$2a$10$7Lbp9dBaijnzMlEDi8DxBeaKYeZpGMTqD4vg8qhOZDgadPMfy5E.q');

INSERT INTO products (category, name, description, price, size, manufacturer, photo)
VALUES ('sneakers', 'ASICS', 'for winter running', 6000, 43, 'Thailand', 'sneakers.png');

INSERT INTO products (category, name, description, price, size, manufacturer, photo)
VALUES ('trousers', 'ADIDAS', 'for activities and sport', 3500, 50, 'Germany', 'trousers.png');

INSERT INTO products (category, name, description, price, size, manufacturer, photo)
VALUES ('jacket', 'KAPPA', 'windstopper', 8000, 54, 'USA', 'jacket.png');

INSERT INTO purchases (product_id, product_price, product_category, product_name, qty, user_id)
VALUES (1, 6000, 'sneakers', 'ASICS', 3, 2);

INSERT INTO purchases (product_id, product_price, product_category, product_name, qty, user_id)
VALUES (2, 3500, 'trousers', 'ADIDAS', 1, 3);

INSERT INTO reviews (user_id, product_id, comment)
VALUES (2, 1, 'потрясающая вещь');

INSERT INTO reviews (user_id, product_id, comment)
VALUES (3, 3, 'рекомендую');