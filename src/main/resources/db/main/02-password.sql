--liquibase formatted sql
--changeset piotrkorniak:101

ALTER TABLE TENANTS
    ADD PASSWORD VARCHAR(32);

