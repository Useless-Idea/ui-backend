--liquibase formatted sql
--changeset psikora:1 context:test

INSERT INTO corporation
    (id, "name", is_blocked, alliance_id)
VALUES (98729426, 'Paridae Nest', false, NULL);

INSERT INTO "character"
    (id, "name", corporation_id, is_token_active, is_block, "version")
VALUES (2120732484, 'Lord Paridae', 98729426, false, false, 0);