CREATE TABLE public.t_user
(
    id        BIGSERIAL PRIMARY KEY,
    username  varchar(255) NOT NULL,
    firstName varchar(255) NOT NULL,
    lastName  varchar(255) NOT NULL,
    email     varchar(255) NOT NULL,
    phone     varchar(255) NOT NULL
);

ALTER TABLE public.t_user
    ADD CONSTRAINT t_user_name_uq UNIQUE (username);

INSERT INTO public.t_user(username, firstName, lastName, email, phone)
VALUES ('vsamarin', 'Владислав', 'Самарин', 'vsamarin@test.ru', '+79060948706');
