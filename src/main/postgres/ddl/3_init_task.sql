CREATE TABLE app.task
(
    uuid uuid,
    dt_create timestamp(3) without time zone,
    dt_update timestamp(3) without time zone,
    project uuid NOT NULL,
    title text NOT NULL,
    description text,
    status text NOT NULL,
    implementer uuid,
    PRIMARY KEY (uuid),
    FOREIGN KEY (project)
        REFERENCES app.project (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    UNIQUE (title)
);


