--liquibase formatted sql
--changeset piotrkorniak:26

ALTER TABLE INVOICES RENAME COLUMN PAYMENT_METOD TO PAYMENT_METHOD;
ALTER TABLE INVOICES ADD COLUMN CORRECTION VARCHAR(32);