--liquibase formatted sql
--changeset piotrkorniak:39

ALTER TABLE INVOICES ADD STATUS CHAR(1);