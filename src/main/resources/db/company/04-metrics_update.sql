--liquibase formatted sql
--changeset piotrkorniak:4

ALTER TABLE METRICS
    ADD BUSINESS_NUMBER VARCHAR(32),
    ADD RC_CODE VARCHAR(4),
    DROP COLUMN US_ID;



