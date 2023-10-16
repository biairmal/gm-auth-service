CREATE TABLE IF NOT EXISTS user_identity (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    date_of_birth DATE,
    profile_picture VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);