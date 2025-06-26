--liquibase formatted sql
--changeset piotrkorniak:27

ALTER TABLE PAYMENTS DROP COLUMN IF EXISTS TYPE;
