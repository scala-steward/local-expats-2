CREATE TABLE post (
    id              UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    message         VARCHAR   NOT NULL,
    target_state    VARCHAR   NOT NULL,
    target_zip_code VARCHAR   NOT NULL,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);