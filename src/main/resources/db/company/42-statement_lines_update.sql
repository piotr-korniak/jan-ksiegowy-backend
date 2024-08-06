--liquibase formatted sql
--changeset piotrkorniak:42

ALTER TABLE STATEMENTS_LINES ALTER COLUMN ITEM_CODE TYPE CHAR(6);

