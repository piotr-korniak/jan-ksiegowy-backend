--liquibase formatted sql
--changeset piotrkorniak:107

ALTER TABLE tenants
    RENAME COLUMN subdomain TO code;


