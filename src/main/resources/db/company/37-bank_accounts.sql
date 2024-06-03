--liquibase formatted sql
--changeset piotrkorniak:37

CREATE TABLE BANK_ACCOUNTS (
    ID UUID,
    NUMBER CHAR(26) NOT NULL,
    CURRENCY CHAR(3),

    PRIMARY KEY (ID)
);