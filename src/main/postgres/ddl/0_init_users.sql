CREATE TABLE app.users
(
    uuid uuid,
    fio text NOT NULL,
    role text NOT NULL,
    status text NOT NULL,
    activation_code uuid,
    email text NOT NULL,
    password text NOT NULL,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    PRIMARY KEY (uuid),
    UNIQUE (email)
);

