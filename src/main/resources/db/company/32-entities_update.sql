--liquibase formatted sql
--changeset piotrkorniak:31

ALTER TABLE ENTITIES DROP CONSTRAINT entities_country_tax_number_date_key;

ALTER TABLE ENTITIES ADD CONSTRAINT entities_type_country_tax_number_date_key UNIQUE (TYPE, COUNTRY, TAX_NUMBER, DATE);

