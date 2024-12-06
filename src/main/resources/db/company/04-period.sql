--liquibase formatted sql
--changeset piotrkorniak:4

CREATE TABLE PERIODS(
    ID VARCHAR(7) NOT NULL,
    PARENT_ID VARCHAR(7),
    TYPE VARCHAR (1) NOT NULL,
    BEGIN DATE,
    "end" DATE,

    PIT BOOLEAN,
    CIT BOOLEAN,
    VAT BOOLEAN,
    JPK BOOLEAN,
    STATUS CHAR(1),

    PRIMARY KEY (ID)
);



