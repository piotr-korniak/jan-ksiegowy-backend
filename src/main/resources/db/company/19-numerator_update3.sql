--liquibase formatted sql
--changeset piotrkorniak:19

ALTER TABLE COUNTERS DROP COLUMN TYPED;
ALTER TABLE NUMERATORS ADD TYPED BOOLEAN DEFAULT FALSE;


