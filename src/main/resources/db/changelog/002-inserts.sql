--liquibase formatted sql
--changeset psikora:1
INSERT INTO "role" ("name", code, description)
VALUES ('Admin', 'ADMIN', 'Role for administrator');


INSERT INTO "permission" ("name", code, description)
VALUES ('ShowChar', 'SHOW_CHAR', 'Show List of all char');

INSERT INTO permission_role (role_uuid, permission_uuid)
VALUES ((select r."uuid" from "role" r where r.code = 'ADMIN'),
        (select p."uuid" from "permission" p where p.code = 'SHOW_CHAR'));
--changeset psikora:2
INSERT INTO corporation
    (id, "name", is_blocked, alliance_id)
VALUES (98067874, 'Useless Idea', false, NULL)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO public.corporation
    (id, "name", is_blocked, alliance_id)
VALUES (98796489, 'Not So Useless', false, NULL)
    ON CONFLICT (id) DO NOTHING;


