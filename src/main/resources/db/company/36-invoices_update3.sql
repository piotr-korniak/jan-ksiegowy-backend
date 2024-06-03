--liquibase formatted sql
--changeset piotrkorniak:38

ALTER TABLE INVOICES ADD PAYMENT_METOD INT;
