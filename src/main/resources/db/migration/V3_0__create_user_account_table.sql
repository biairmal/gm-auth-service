CREATE TABLE IF NOT EXISTS user_account (
    id UUID PRIMARY KEY,
    user_identity_id UUID REFERENCES user_identity(id),
    email_address VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_verified BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);