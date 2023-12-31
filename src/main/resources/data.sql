set foreign_key_checks=0;
drop table if exists comments cascade;
drop table if exists tasks cascade;
drop table if exists tasks_executor cascade;
drop table if exists tasks_comment cascade;
drop table if exists users cascade;
set foreign_key_checks=1;

create table if not exists comments
(
    id        integer not null auto_increment,
    comment   varchar(255),
    author_id integer,
    primary key (id)
);
create table if not exists tasks
(
    id          integer not null auto_increment,
    description varchar(255),
    priority    varchar(255),
    status      varchar(255),
    title       varchar(255),
    author_id   integer,
    primary key (id)
);
create table if not exists tasks_comment
(
    task_id    integer not null,
    comment_id integer not null
);
create table if not exists tasks_executor
(
    task_id     integer not null,
    executor_id integer not null
);
create table if not exists users
(
    id         integer not null auto_increment,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    position   varchar(255),
    primary key (id)
);
alter table tasks_comment
    add constraint unique (comment_id);
alter table comments
    add constraint foreign key (author_id) references users (id);
alter table tasks
    add constraint foreign key (author_id) references users (id);
alter table tasks_comment
    add constraint foreign key (comment_id) references comments (id);
alter table tasks_comment
    add constraint foreign key (task_id) references tasks (id);
alter table tasks_executor
    add constraint foreign key (executor_id) references users (id);
alter table tasks_executor
    add constraint foreign key (task_id) references tasks (id);

INSERT INTO users (email, first_name, last_name, password, position)
VALUES ('test1@mail.com', 'test', 'test', 'test', 'test'),
       ('test2@mail.com', 'test', 'test', 'test', 'test');
INSERT INTO tasks (description, priority, status, title, author_id)
VALUES ('test', 'test', 'Awaits', 'test1', 1),
       ('test', 'test', 'Awaits', 'test2', 2);
INSERT INTO tasks_executor (task_id, executor_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);
INSERT INTO comments (comment, author_id)
VALUES ('test', 1),
       ('test', 2);
INSERT INTO tasks_comment (task_id, comment_id)
VALUES (1, 1),
       (2, 2)