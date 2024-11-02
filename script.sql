CREATE TABLE instrument (
    id BIGINT PRIMARY KEY,
    available BOOLEAN,
    name VARCHAR(255),
    price_per_day NUMERIC(19, 2),
    type VARCHAR(255)
);

CREATE TABLE instrument_category (
    id BIGINT PRIMARY KEY,
    category_name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE customer (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255),
    full_name VARCHAR(255),
    phone_number VARCHAR(255)
);

CREATE TABLE model_user (
    id BIGINT PRIMARY KEY,
    active BOOLEAN,
    password VARCHAR(255),
    username VARCHAR(255)
);

CREATE TABLE user_role (
    user_id BIGINT,
    roles VARCHAR(255),
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES model_user(id)
);

CREATE TABLE supplier (
    id BIGINT PRIMARY KEY,
    company_name VARCHAR(255),
    contact_name VARCHAR(255),
    phone_number VARCHAR(255)
);

CREATE TABLE maintenance (
    id BIGINT PRIMARY KEY,
    description VARCHAR(255),
    maintenance_date DATE,
    instrument_id BIGINT,
    FOREIGN KEY (instrument_id) REFERENCES instrument(id)
);

CREATE TABLE rental (
    id BIGINT PRIMARY KEY,
    rental_date DATE,
    return_date DATE,
    user_id BIGINT,
    instrument_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES customer(id),
    FOREIGN KEY (instrument_id) REFERENCES instrument(id)
);

CREATE TABLE payment (
    id BIGINT PRIMARY KEY,
    amount NUMERIC(19, 2),
    payment_date DATE,
    rental_id BIGINT,
    FOREIGN KEY (rental_id) REFERENCES rental(id)
);
