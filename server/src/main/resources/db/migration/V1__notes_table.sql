CREATE TABLE notes (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    title TEXT,
    content TEXT NOT NULL,
    created_at TIMESTAMP  NOT NULL,
    updated_at TIMESTAMP  NOT NULL,
    status VARCHAR(1) NOT NULL
);
