CREATE TABLE IF NOT EXISTS users (
    email TEXT PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS surveys (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_email TEXT NOT NULL REFERENCES users(email),
    hear_about_us TEXT NOT NULL,
    contact_method TEXT NOT NULL,
    receive_announcements BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
