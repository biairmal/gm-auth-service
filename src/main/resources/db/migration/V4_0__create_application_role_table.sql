CREATE TABLE IF NOT EXISTS application_role (
    id UUID PRIMARY KEY,
    role_name varchar(255) NOT NULL,
    role_description varchar(255)
);