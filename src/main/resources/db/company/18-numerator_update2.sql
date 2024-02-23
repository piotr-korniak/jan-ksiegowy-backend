--liquibase formatted sql
--changeset piotrkorniak:18

ALTER TABLE COUNTERS ADD TYPED BOOLEAN DEFAULT FALSE;

