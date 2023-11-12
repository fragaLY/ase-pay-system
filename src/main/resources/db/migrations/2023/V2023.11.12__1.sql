CREATE SCHEMA IF NOT EXISTS asepay;

CREATE TABLE IF NOT EXISTS asepay."user"
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS asepay.wallet
(
    id       UUID           NOT NULL,
    owner_id BIGINT         NOT NULL,
    currency VARCHAR(4)     NOT NULL,
    balance  NUMERIC(19, 4) NOT NULL,

    PRIMARY KEY (id, owner_id, currency),
    FOREIGN KEY (owner_id) REFERENCES asepay.user (id)
);

CREATE TABLE IF NOT EXISTS asepay."transaction"
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

-- Insert 3 users
INSERT INTO asepay.user (username)
VALUES ('User1');
INSERT INTO asepay.user (username)
VALUES ('User2');
INSERT INTO asepay.user (username)
VALUES ('User3');

-- Insert wallets for User1
INSERT INTO asepay.wallet (id, owner_id, currency, balance)
VALUES (gen_random_uuid(), 1, 'USD', 10001),
       (gen_random_uuid(), 1, 'EUR', 200),
       (gen_random_uuid(), 1, 'BRL', 300),
       (gen_random_uuid(), 1, 'ARS', 400),
       (gen_random_uuid(), 1, 'UYU', 500);

-- Insert wallets for User2
INSERT INTO asepay.wallet (id, owner_id, currency, balance)
VALUES (gen_random_uuid(), 2, 'USD', 1000),
       (gen_random_uuid(), 2, 'EUR', 2000),
       (gen_random_uuid(), 2, 'BRL', 3000),
       (gen_random_uuid(), 2, 'ARS', 4000),
       (gen_random_uuid(), 2, 'UYU', 5000);

-- Insert wallets for User3 with only USD currency
INSERT INTO asepay.wallet (id, owner_id, currency, balance)
VALUES (gen_random_uuid(), 3, 'USD', 10000);