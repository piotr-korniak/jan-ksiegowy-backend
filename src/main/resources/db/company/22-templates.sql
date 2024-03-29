--liquibase formatted sql
--changeset piotrkorniak:22

CREATE TABLE TEMPLATES (
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    TEMPLATE_ID UUID,
    CODE VARCHAR(24),
    DATE DATE NOT NULL,
    TYPE VARCHAR(1) NOT NULL,
    KIND VARCHAR(1) NOT NULL,
    CONTEXT VARCHAR(1) NOT NULL,
    REGISTER_ID UUID,
    NAME VARCHAR(128),

    PRIMARY KEY (ID)
);