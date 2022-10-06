CREATE TABLE post (
    id              BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    message         VARCHAR     NOT NULL,
    target_state    VARCHAR     NOT NULL,
    target_zip_code VARCHAR     NOT NULL,
    created_at      timestamptz NOT NULL DEFAULT now()
);