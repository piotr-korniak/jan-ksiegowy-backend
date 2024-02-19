--liquibase formatted sql
--changeset piotrkorniak:15

ALTER  TABLE  INVOICES RENAME COLUMN vat_register_id TO register_id;;
