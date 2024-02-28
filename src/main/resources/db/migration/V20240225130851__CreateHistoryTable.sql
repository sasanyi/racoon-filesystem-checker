CREATE SCHEMA IF NOT EXISTS rcswfilesystem;

CREATE TABLE IF NOT EXISTS rcswfilesystem.history
(
    requestBy   varchar      not null,
    id          varchar(255) not null constraint history_pk primary key,
    result      text,
    requestedOn timestamp default now(),
    path        text
);
