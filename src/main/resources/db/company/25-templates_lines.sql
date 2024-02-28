--liquibase formatted sql
--changeset piotrkorniak:25

CREATE TABLE TEMPLATES_LINES (
     ID UUID,
     TEMPLATE_ID BIGINT,
     TYPE CHAR(1),
     NO INT,
     ACCOUNT_ID UUID,
     FUNCTION VARCHAR(32),
     PAGE CHAR(1),

     PRIMARY KEY (ID)
);