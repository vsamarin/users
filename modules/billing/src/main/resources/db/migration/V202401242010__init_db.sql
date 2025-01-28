CREATE TABLE billing.t_account
(
    user_id BIGINT PRIMARY KEY,
    balance BIGINT DEFAULT 0 NOT NULL
);
