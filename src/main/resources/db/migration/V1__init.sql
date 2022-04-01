DROP TABLE IF EXISTS products;
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    price BIGINT
);
INSERT INTO products (title, description, price)
VALUES
('Samsung Galaxy S12', '8GB Ram & 128GB storage, 6.4 Inches Display', 1500),
('iPhone 12', '6.1-inch and 5.4-inch sizes with identical features', 2000),
('iMac 27', 'quad-core Intel Core i5 processor, which is clocked at 3.4 GHz', 4000),
('iPad 8', 'iPad eighth-generation with Smart Keyboard', 1800),
('Samsung A03s', '6.5″ display, 5000 mAh battery, 64 GB storage', 500),
('Samsung A12', 'More display means more room to play', 800),
('Samsung A10', 'More display means more room to play', 700),
('Samsung A14', 'More display means more room to play', 900);
