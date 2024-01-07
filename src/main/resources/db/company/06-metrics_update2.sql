--liquibase formatted sql
--changeset piotrkorniak:6

ALTER TABLE METRICS
    ADD VAT_PL BOOLEAN;



