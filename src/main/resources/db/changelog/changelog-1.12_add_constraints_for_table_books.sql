--liquibase formatted sql

--changeset ikulaga:changelog-1.12_add_constraints_for_table_books
alter table books
    add constraint check_publish_year_not_negative_if_exists check (publish_year is null or publish_year >= 0);
alter table books
    add constraint check_quantity_available_not_negative check (quantity_available >= 0);