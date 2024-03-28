CREATE TABLE users (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email      VARCHAR NOT NULL CHECK ( length(trim(email)) >= 2 ),
    first_name VARCHAR NOT NULL CHECK ( length(trim(first_name)) >= 2 ),
    last_name  VARCHAR NOT NULL CHECK ( length(trim(last_name)) >= 2 )
);