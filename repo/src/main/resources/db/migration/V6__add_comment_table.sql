CREATE TABLE comment (
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    post_id    BIGINT references post(id),
    message    VARCHAR     NOT NULL check ( length(trim(message)) >= 2 ),
    created_at timestamptz NOT NULL DEFAULT now()
);