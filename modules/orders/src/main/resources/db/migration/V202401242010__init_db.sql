CREATE TABLE orders.t_order
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT       NOT NULL,
    price   BIGINT       NOT NULL,
    product VARCHAR(255) NOT NULL
);
