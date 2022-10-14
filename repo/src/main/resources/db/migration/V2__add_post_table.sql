CREATE TABLE post (
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title      VARCHAR     NOT NULL CHECK ( length(trim(title)) >= 2 ),
    message    VARCHAR,
    state      VARCHAR     NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now()
);