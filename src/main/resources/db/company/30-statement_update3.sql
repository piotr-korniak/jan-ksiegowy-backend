--liquibase formatted sql
--changeset piotrkorniak:30

ALTER TABLE STATEMENTS ALTER COLUMN PATTERN_ID TYPE VARCHAR(16);

