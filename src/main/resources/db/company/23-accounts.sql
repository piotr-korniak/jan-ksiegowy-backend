--liquibase formatted sql
--changeset piotrkorniak:23

CREATE TABLE ACCOUNTS (
    ID UUID,
    PARENT_ID UUID,
    NUMBER VARCHAR(24),
    TYPE VARCHAR(1) NOT NULL,
    NAME VARCHAR(128),

    PRIMARY KEY (ID)
);