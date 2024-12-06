--liquibase formatted sql
--changeset piotrkorniak:7

CREATE TABLE STATEMENTS(
    ID UUID,
    TYPE CHAR(1),
    PERIOD_ID VARCHAR(7),
    PATTERN_ID VARCHAR(16),
    METRIC_ID DATE,
    NO INT,
    DATE DATE NOT NULL,
    CREATED TIMESTAMP,
    STATUS CHAR(1),
    XML TEXT, --- CLOB, --- (256 K),

    UNIQUE (ID)
);