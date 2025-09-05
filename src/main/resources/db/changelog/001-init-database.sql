--liquibase formatted sql
--changeset psikora:1
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset psikora:2

CREATE TABLE alliance
(
    id         BIGINT            NOT NULL,
    "name"     varchar NULL,
    is_blocked bool DEFAULT true NOT NULL,
    CONSTRAINT alliance_pk PRIMARY KEY (id)
);

CREATE TABLE "permission"
(
    "uuid"      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "name"      varchar NOT NULL,
    code        varchar NOT NULL,
    description varchar NULL,
    CONSTRAINT permission_unique UNIQUE (code),
    CONSTRAINT permission_name UNIQUE (name)
);

CREATE TABLE "role"
(
    "uuid"      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    "name"      varchar NOT NULL,
    code        varchar NOT NULL,
    description varchar NULL,
    CONSTRAINT role_unique UNIQUE (code),
    CONSTRAINT role_name UNIQUE (name)
);

CREATE TABLE corporation
(
    id          BIGINT            NOT NULL,
    "name"      varchar           NOT NULL,
    is_blocked  bool DEFAULT true NOT NULL,
    alliance_id BIGINT NULL,
    CONSTRAINT corporation_pk PRIMARY KEY (id),
    CONSTRAINT corporation_alliance_fk FOREIGN KEY (alliance_id) REFERENCES alliance (id)
);


CREATE TABLE permission_role
(
    role_uuid       uuid NOT NULL,
    permission_uuid uuid NOT NULL,
    CONSTRAINT permission_role_pkey PRIMARY KEY (permission_uuid, role_uuid),
    CONSTRAINT permission_role_permission_uuid_fkey FOREIGN KEY (permission_uuid) REFERENCES "permission" ("uuid"),
    CONSTRAINT permission_role_role_uuid_fkey FOREIGN KEY (role_uuid) REFERENCES "role" ("uuid")
);

CREATE TABLE "character"
(
    id              BIGINT             NOT NULL,
    "name"          varchar            NOT NULL,
    corporation_id  BIGINT             NOT NULL,
    is_token_active bool DEFAULT false NOT NULL,
    is_block        bool DEFAULT true  NOT NULL,
    CONSTRAINT character_pk PRIMARY KEY (id),
    CONSTRAINT character_corporation_fk FOREIGN KEY (corporation_id) REFERENCES corporation (id)
);

CREATE TABLE character_role
(
    character_id BIGINT NOT NULL,
    role_uuid    uuid   NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (character_id, role_uuid),
    CONSTRAINT user_role_character_id_fkey FOREIGN KEY (character_id) REFERENCES "character" (id),
    CONSTRAINT user_role_role_uuid_fkey FOREIGN KEY (role_uuid) REFERENCES "role" ("uuid")
);

--changeset psikora:3

CREATE TABLE esi_token
(
    char_id       BIGINT       NOT NULL,
    exp_date      TIMESTAMP    NOT NULL,
    refresh_token varchar(255) NOT NULL,
    jwt           text         NOT NULL,
    PRIMARY KEY (char_id)
);

--changeset psikora:4
ALTER TABLE "character"
    ADD "version" BIGINT DEFAULT 0 NOT NULL;

--changeset psikora:5
ALTER TABLE esi_token
    ADD COLUMN features TEXT;

--changeset psikora:6
CREATE TABLE "item_type"
(
    id     BIGINT  NOT NULL,
    "name" varchar NOT NULL
);
--changeset psikora:7
CREATE TABLE "fit"
(
    "uuid" UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    eft    varchar NOT NULL
);
--changeset psikora:8
ALTER TABLE fit
    ADD COLUMN pilots JSONB,
    ADD COLUMN ship_name varchar,
    ADD COLUMN fit_name varchar
    ;
--changeset psikora:9
ALTER TABLE fit
    ADD COLUMN ship_id BIGINT
;

