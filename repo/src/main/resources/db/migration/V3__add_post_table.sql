CREATE TABLE post (
    id              BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title           VARCHAR     NOT NULL check ( length(title) > 2 ),
    message         VARCHAR     NOT NULL,
    target_state    VARCHAR     NOT NULL,
    created_at      timestamptz NOT NULL DEFAULT now()
);