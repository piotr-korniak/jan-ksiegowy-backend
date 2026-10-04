--liquibase formatted sql
--changeset piotrkorniak:103

ALTER TABLE users
    ADD COLUMN last_domain VARCHAR(96),
    ADD COLUMN last_schema VARCHAR(96);
--rollback ALTER TABLE users DROP COLUMN last_tenant_code, DROP COLUMN last_schema_name;

