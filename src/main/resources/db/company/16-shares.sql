--liquibase formatted sql
--changeset piotrkorniak:16

CREATE TABLE SHARES (
    ID UUID,
    EQUITY DECIMAL(5,2) NOT NULL,

    PRIMARY KEY (ID)
);

