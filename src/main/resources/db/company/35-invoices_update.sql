--liquibase formatted sql
--changeset piotrkorniak:35

ALTER TABLE INVOICES
    ADD PERIOD_ID VARCHAR(7);
