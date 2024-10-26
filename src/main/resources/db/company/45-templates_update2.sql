--liquibase formatted sql
--changeset piotrkorniak:45

ALTER TABLE TEMPLATES ADD ENTITY_TYPE CHAR(1);