--liquibase formatted sql
--changeset piotrkorniak:14

CREATE TABLE CONTRACTS (
    ID UUID,

    ENTITY_ID BIGINT,

    TYPE CHAR (1) NOT NULL,
    BEGIN DATE,
    "end" DATE,
    NUMBER VARCHAR(32),
    SALARY DECIMAL(12,2) NOT NULL,

    PRIMARY KEY (ID)
);


CREATE TABLE PAYSLIPS (
    ID UUID,
    CONTRACT_ID UUID,

    PRIMARY KEY (ID)
);
