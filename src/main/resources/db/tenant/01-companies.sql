--liquibase formatted sql
--changeset piotrkorniak:100

CREATE TABLE COMPANIES (
   ID UUID,
   CODE VARCHAR(32),
   NAME VARCHAR(64),

   PRIMARY KEY (ID)
);

