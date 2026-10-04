--liquibase formatted sql
--changeset piotrkorniak:104

CREATE TABLE MEMBERSHIPS (
    ID              UUID NOT NULL,
    USER_ID         UUID NOT NULL,
    TENANT_ID       UUID NOT NULL,
    SCHEMA_NAME     VARCHAR(96) NOT NULL,
    GRANTED_AT      TIMESTAMP NOT NULL DEFAULT now(),
    GRANTED_BY_USER UUID,
    REVOKED_AT      TIMESTAMP,
    ROLE            VARCHAR(1) NOT NULL DEFAULT 'N',
    CONSTRAINT pk_memberships PRIMARY KEY (id),
    CONSTRAINT uq_mem_user_tenant_schema UNIQUE (user_id, tenant_id, schema_name),
    CONSTRAINT fk_mem_user_id FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_mem_tenant_id FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

-- aktywne dostępy usera (najczęstsze zapytanie przy logowaniu)
CREATE INDEX idx_mem_user_id_active
    ON MEMBERSHIPS(user_id, revoked_at);

-- wszyscy userzy z dostępem do danej firmy
CREATE INDEX idx_mem_tenant_schema
    ON MEMBERSHIPS(tenant_id, schema_name);
--rollback DROP TABLE user_company_access;


