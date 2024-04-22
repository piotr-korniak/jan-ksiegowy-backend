--liquibase formatted sql
--changeset piotrkorniak:34

ALTER TABLE STATEMENTS ADD STATUS CHAR(1);

