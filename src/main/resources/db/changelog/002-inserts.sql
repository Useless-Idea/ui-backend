--liquibase formatted sql
--changeset psikora:1
INSERT INTO "role" ("name", code, description)
VALUES ('Admin', 'ADMIN', 'Role for administrator');


INSERT INTO "permission" ("name", code, description)
VALUES ('ShowChar', 'SHOW_CHAR', 'Show List of all char');

INSERT INTO permission_role (role_uuid, permission_uuid)
VALUES ((select r."uuid" from "role" r where r.code = 'ADMIN'),
        (select p."uuid" from "permission" p where p.code = 'SHOW_CHAR'));


