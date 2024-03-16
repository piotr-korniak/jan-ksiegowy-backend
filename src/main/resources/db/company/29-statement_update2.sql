--liquibase formatted sql
--changeset piotrkorniak:29

ALTER TABLE STATEMENTS
    ADD TYPE CHAR(1),
    ADD VALUE_1 DECIMAL(12,2),
    ADD VALUE_2 DECIMAL(12,2);
