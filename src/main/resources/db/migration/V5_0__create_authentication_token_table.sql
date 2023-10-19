CREATE TABLE IF NOT EXISTS authentication_token (
    id UUID PRIMARY KEY,
    user_identity_id UUID REFERENCES user_identity(id),
    access_token varchar(255) UNIQUE NOT NULL,
    refresh_token varchar(255) UNIQUE,
    is_expired boolean DEFAULT false,
    is_revoked boolean DEFAULT false,
    created_at TIMESTAMP,
    expired_at TIMESTAMP
);
