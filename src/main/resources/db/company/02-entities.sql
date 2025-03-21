--liquibase formatted sql
--changeset piotrkorniak:2

CREATE TABLE ENTITIES (
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    ENTITY_ID UUID NOT NULL,
    DATE DATE NOT NULL,
    TYPE CHAR(1),
    ACCOUNT_NUMBER VARCHAR(12),
    TAX_NUMBER VARCHAR(13) NOT NULL,
    REGISTRATION_NUMBER VARCHAR(32),
    NAME VARCHAR(128),
    ADDRESS VARCHAR(64),
    TOWN VARCHAR(32),
    POSTCODE VARCHAR(7),
    COUNTRY CHAR(2) DEFAULT 'PL' NOT NULL,
    SUPPLIER BOOLEAN DEFAULT FALSE,
    CUSTOMER BOOLEAN DEFAULT FALSE,
    VAT_PL BOOLEAN DEFAULT FALSE,
    VAT_UE BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (ID),
    UNIQUE (TYPE, COUNTRY, TAX_NUMBER, DATE)
);
