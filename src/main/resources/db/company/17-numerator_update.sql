--liquibase formatted sql
--changeset piotrkorniak:15

ALTER TABLE COUNTERS RENAME COLUMN years TO year;
