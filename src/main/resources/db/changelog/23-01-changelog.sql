-- liquibase formatted sql

-- changeset felipe:1787456631909-2
ALTER TABLE carro
    ADD cor VARCHAR(255) NULL;

-- changeset felipe:1787456631909-3
DROP TABLE revinfo_seq;

