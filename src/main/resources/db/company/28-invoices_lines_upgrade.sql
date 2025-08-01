--liquibase formatted sql
--changeset piotrkorniak:28

ALTER TABLE INVOICES_LINES RENAME COLUMN AMOUNT TO NET;
