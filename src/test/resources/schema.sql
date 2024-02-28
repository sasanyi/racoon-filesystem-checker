CREATE SCHEMA IF NOT EXISTS rcswfilesystem;

CREATE TABLE IF NOT EXISTS rcswfilesystem.history
(
    requestBy   varchar      not null,
    id          uuid not null constraint history_pk primary key,
    result      text,
    requestedOn timestamp default now(),
    path        text,
    extension   VARCHAR(20) DEFAULT '*'
);
