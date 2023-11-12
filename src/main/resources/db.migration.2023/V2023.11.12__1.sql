CREATE SCHEMA IF NOT EXISTS asepay;

CREATE TABLE asepay.user
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE asepay.wallet
(
    id       UUID           NOT NULL,
    owner_id BIGINT         NOT NULL,
    currency VARCHAR(4)     NOT NULL,
    balance  NUMERIC(19, 4) NOT NULL,

    PRIMARY KEY (id, owner_id, currency),
    FOREIGN KEY (owner_id) REFERENCES asepay.user (id)
);

CREATE TABLE asepay.transaction
(
    id                 UUID           NOT NULL PRIMARY KEY,
    sender_id          BIGINT         NOT NULL,
    recipient_id       BIGINT         NOT NULL,
    amount             NUMERIC(19, 2) NOT NULL,
    source_currency    VARCHAR(4)     NOT NULL,
    target_currency    VARCHAR(4)     NOT NULL,
    exchange_rate      NUMERIC(19, 2) NOT NULL,
    status             VARCHAR(20)    NOT NULL,
    creation_date_time TIMESTAMP      NOT NULL,
    update_date_time   TIMESTAMP      NOT NULL
);

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Insert 3 users
INSERT INTO asepay.user (username)
VALUES ('User1');
INSERT INTO asepay.user (username)
VALUES ('User2');
INSERT INTO asepay.user (username)
VALUES ('User3');

-- Insert wallets for User1
INSERT INTO asepay.wallet (id, owner_id, currency, balance)
VALUES (uuid_generate_v4(), 1, 'USD', 10001),
       (uuid_generate_v4(), 1, 'EUR', 200),
       (uuid_generate_v4(), 1, 'BRL', 300),
       (uuid_generate_v4(), 1, 'ARS', 400),
       (uuid_generate_v4(), 1, 'UYU', 500);

-- Insert wallets for User2
INSERT INTO asepay.wallet (id, owner_id, currency, balance)
VALUES (uuid_generate_v4(), 2, 'USD', 1000),
       (uuid_generate_v4(), 2, 'EUR', 2000),
       (uuid_generate_v4(), 2, 'BRL', 3000),
       (uuid_generate_v4(), 2, 'ARS', 4000),
       (uuid_generate_v4(), 2, 'UYU', 5000);

-- Insert wallets for User3 with only USD currency
INSERT INTO asepay.wallet (id, owner_id, currency, balance)
VALUES (uuid_generate_v4(), 3, 'USD', 10000);