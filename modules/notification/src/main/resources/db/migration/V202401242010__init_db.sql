CREATE TABLE notification.t_notification
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT       NOT NULL,
    message VARCHAR(255) NOT NULL
);
