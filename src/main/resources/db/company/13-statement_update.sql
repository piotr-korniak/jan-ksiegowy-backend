--liquibase formatted sql
--changeset piotrkorniak:13

ALTER TABLE STATEMENTS
    ADD CREATED TIMESTAMP,
    ADD METRIC_ID DATE;