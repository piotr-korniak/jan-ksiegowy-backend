--liquibase formatted sql
--changeset piotrkorniak:6

CREATE TABLE INVOICES_LINES (
        ID UUID,
        INVOICE_ID UUID REFERENCES INVOICES(ID),
        NO INT,

        ITEM_ID BIGINT REFERENCES ITEMS(ID),

        AMOUNT DECIMAL(12,2) NOT NULL,
        TAX DECIMAL(12,2) NOT NULL,
        TAX_RATE CHAR(2),

        BASE DECIMAL(12,2) NOT NULL,
        VAT DECIMAL(12,2) NOT NULL,
        CIT DECIMAL(12,2) NOT NULL,

        PRIMARY KEY (ID)
);