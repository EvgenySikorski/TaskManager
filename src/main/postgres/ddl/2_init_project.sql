CREATE TABLE app.project
(
    uuid uuid,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    name text NOT NULL,
    description text,
    manager uuid NOT NULL,
    status text NOT NULL,
    PRIMARY KEY (uuid),
    UNIQUE (name)
);

CREATE TABLE app.project_user
(
    uuid_project uuid NOT NULL,
    uuid_user uuid NOT NULL,
    FOREIGN KEY (uuid_project)
        REFERENCES app.project (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

