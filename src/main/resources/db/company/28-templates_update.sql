--liquibase formatted sql
--changeset piotrkorniak:28

ALTER TABLE TEMPLATES
    ADD DOCUMENT_TYPE VARCHAR(2),
    DROP COLUMN TYPE,
    DROP COLUMN KIND,
    DROP COLUMN CONTEXT;

ALTER TABLE TEMPLATES_LINES
    ADD REGISTER_TYPE VARCHAR(1);
