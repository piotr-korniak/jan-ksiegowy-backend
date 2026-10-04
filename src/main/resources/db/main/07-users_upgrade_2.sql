--liquibase formatted sql
--changeset piotrkorniak:106

ALTER TABLE users
    DROP COLUMN last_domain,
    DROP COLUMN last_schema,
    ADD COLUMN last_membership_id UUID REFERENCES MEMBERSHIPS(id);

