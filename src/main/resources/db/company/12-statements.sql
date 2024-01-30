--liquibase formatted sql
--changeset piotrkorniak:12

CREATE TABLE STATEMENTS(
    ID UUID,
    PERIOD_ID VARCHAR(7),
    PATTERN_ID VARCHAR(7),
    NO INT,
    DATE DATE NOT NULL,
    XML TEXT, --- CLOB, --- (256 K),

    UNIQUE (ID)
);