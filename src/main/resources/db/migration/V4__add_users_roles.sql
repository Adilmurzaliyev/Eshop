CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR (255)
);

INSERT INTO roles (name)
VALUES
       ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_GUEST');


CREATE TABLE users_roles (
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    role_id BIGINT
);