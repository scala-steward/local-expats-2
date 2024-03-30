CREATE TABLE comment
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    post_id    BIGINT REFERENCES post (id),
    message    VARCHAR     NOT NULL CHECK ( length(trim(message)) >= 2 ),
    created_at timestamptz NOT NULL DEFAULT now()
);