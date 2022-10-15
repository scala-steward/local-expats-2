CREATE TABLE post (
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title       VARCHAR     NOT NULL CHECK ( length(trim(title)) >= 2 ),
    message     VARCHAR,
    location_id BIGINT      NOT NULL REFERENCES location(id),
    created_at  timestamptz NOT NULL DEFAULT now()
);