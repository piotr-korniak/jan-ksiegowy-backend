--liquibase formatted sql
--changeset piotrkorniak:12

CREATE TABLE TEMPLATES_LINES (
    ID UUID,
    TEMPLATE_ID BIGINT,
    TYPE CHAR(1),
    SETTLEMENT_TYPE CHAR(1),
    NO INT,
    ACCOUNT_ID UUID,
    FUNCTION VARCHAR(32),
    SIDE CHAR(1),
    DESCRIPTION VARCHAR(96),

    PRIMARY KEY (ID)
);