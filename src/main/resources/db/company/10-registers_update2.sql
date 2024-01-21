--liquibase formatted sql
--changeset piotrkorniak:11

ALTER TABLE REGISTERS
ALTER COLUMN KIND TYPE CHAR(1);