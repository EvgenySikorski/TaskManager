CREATE TABLE app.audit
(
    uuid uuid,
    dt_create timestamp(3) without time zone,
    user_uuid uuid NOT NULL,
    email text NOT NULL,
    fio text NOT NULL,
    role text NOT NULL,
    text text NOT NULL,
    type text NOT NULL,
    id text NOT NULL,
    PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.audit
    OWNER to postgres;