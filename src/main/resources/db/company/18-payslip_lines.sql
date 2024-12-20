--liquibase formatted sql
--changeset piotrkorniak:18

CREATE TABLE PAYSLIPS_LINES (
    ID UUID,
    PAYSLIP_ID UUID,

    ITEM_CODE CHAR(6),
    AMOUNT DECIMAL(12,2) NOT NULL,

    FOREIGN KEY (PAYSLIP_ID) REFERENCES PAYSLIPS( ID),
    PRIMARY KEY (ID)
);