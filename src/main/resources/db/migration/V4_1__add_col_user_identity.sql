ALTER TABLE user_identity
    ADD COLUMN IF NOT EXISTS application_role_id UUID;

ALTER TABLE user_identity
    ADD CONSTRAINT fk_application_role_id FOREIGN KEY (application_role_id) REFERENCES application_role(id);
