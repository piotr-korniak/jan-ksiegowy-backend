--liquibase formatted sql
--changeset piotrkorniak:36

ALTER TABLE INVOICES
    DROP COLUMN TYPE,
    ADD XML TEXT; --- CLOB, --- (256 K),
