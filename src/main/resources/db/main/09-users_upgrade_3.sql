--liquibase formatted sql
--changeset piotrkorniak:108

ALTER TABLE users
    ADD COLUMN first_name VARCHAR(48),
    ADD COLUMN last_name VARCHAR(48);

