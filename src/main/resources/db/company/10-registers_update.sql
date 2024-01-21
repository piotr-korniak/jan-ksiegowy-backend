--liquibase formatted sql
--changeset piotrkorniak:10

ALTER TABLE REGISTERS
ALTER COLUMN KIND TYPE VARCHAR(1);