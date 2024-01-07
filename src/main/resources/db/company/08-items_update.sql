--liquibase formatted sql
--changeset piotrkorniak:8

ALTER TABLE ITEMS
    ADD MEASURE VARCHAR(10);