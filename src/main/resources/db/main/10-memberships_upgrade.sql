--liquibase formatted sql
--changeset piotrkorniak:109

ALTER TABLE memberships
    RENAME COLUMN schema_name TO company_code;

ALTER TABLE memberships
    ADD COLUMN company_name VARCHAR(96),
    ALTER COLUMN company_code TYPE VARCHAR(24)
        USING substr( company_code, 1, 24);
