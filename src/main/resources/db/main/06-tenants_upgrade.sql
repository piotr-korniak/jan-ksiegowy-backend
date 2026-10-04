--liquibase formatted sql
--changeset piotrkorniak:105

ALTER TABLE tenants
    RENAME COLUMN code TO subdomain;
--rollback ALTER TABLE users DROP COLUMN last_tenant_code, DROP COLUMN last_schema_name;

