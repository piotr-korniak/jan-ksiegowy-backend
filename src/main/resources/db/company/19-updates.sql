--liquibase formatted sql
--changeset piotrkorniak:19

CREATE TABLE UPDATES (

    ID SERIAL PRIMARY KEY,
    STEP_URL CHAR(48) NOT NULL,
    PARAMS CHAR(48),
    EXECUTED_AT TIMESTAMP NOT NULL

);