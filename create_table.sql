create table class
(
    id        int auto_increment
        primary key,
    name      varchar(255)         not null,
    is_active tinyint(1) default 1 null
);

create table faculty
(
    id        int auto_increment
        primary key,
    name      varchar(255)         not null,
    is_active tinyint(1) default 1 null
);

create table pointgroup
(
    id        int auto_increment
        primary key,
    name      varchar(255) not null,
    content   text         null,
    max_point int          null,
    constraint id_UNIQUE
        unique (id)
);

create table activity
(
    id            int auto_increment
        primary key,
    name          varchar(255)                         not null,
    pointgroup_id int                                  null,
    is_active     tinyint(1) default 1                 null,
    created_date  timestamp  default CURRENT_TIMESTAMP null,
    updated_date  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint activity_ibfk_1
        foreign key (pointgroup_id) references pointgroup (id)
);

create index pointgroup_id
    on activity (pointgroup_id);

create table mission
(
    id           int auto_increment
        primary key,
    name         varchar(255)                         not null,
    activity_id  int                                  null,
    point        int                                  null,
    content      text                                 null,
    start_date   date                                 null,
    end_date     date                                 null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint mission_ibfk_1
        foreign key (activity_id) references activity (id)
);

create index activity_id
    on mission (activity_id);

create table user
(
    id         int auto_increment
        primary key,
    first_name varchar(100)                                          not null,
    last_name  varchar(100)                                          not null,
    email      varchar(255)                                          not null,
    username   varchar(50)                                           not null,
    password   varchar(255)                                          not null,
    phone      varchar(20)                                           null,
    avatar     varchar(255)                                          null,
    gender     enum ('Male', 'Female', 'Other')                      null,
    dob        date                                                  null,
    is_active  tinyint(1) default 1                                  null,
    role       enum ('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_ASSISTANT') null
);

create table assistant
(
    id         int not null
        primary key,
    faculty_id int null,
    constraint assistant_ibfk_1
        foreign key (id) references user (id),
    constraint assistant_ibfk_2
        foreign key (faculty_id) references faculty (id)
);

create index faculty_id
    on assistant (faculty_id);

create table post
(
    id           int auto_increment
        primary key,
    content      text                                 null,
    image        varchar(255)                         null,
    activity_id  int                                  null,
    user_id      int                                  null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint post_ibfk_1
        foreign key (activity_id) references activity (id),
    constraint post_ibfk_2
        foreign key (user_id) references user (id)
);

create index activity_id
    on post (activity_id);

create table student
(
    id         int         not null
        primary key,
    student_id varchar(50) not null,
    class_id   int         null,
    faculty_id int         null,
    constraint student_ibfk_1
        foreign key (id) references user (id),
    constraint student_ibfk_2
        foreign key (class_id) references class (id),
    constraint student_ibfk_3
        foreign key (faculty_id) references faculty (id)
);

create table comment
(
    id           int auto_increment
        primary key,
    student_id   int                                  null,
    post_id      int                                  null,
    content      text                                 null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint comment_ibfk_1
        foreign key (student_id) references student (id),
    constraint comment_ibfk_2
        foreign key (post_id) references post (id)
);

create index post_id
    on comment (post_id);

create index student_id
    on comment (student_id);

create table `like`
(
    id           int auto_increment
        primary key,
    student_id   int                                  null,
    post_id      int                                  null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint like_ibfk_1
        foreign key (student_id) references student (id),
    constraint like_ibfk_2
        foreign key (post_id) references post (id)
);

create index post_id
    on `like` (post_id);

create index student_id
    on `like` (student_id);

create table missingreport
(
    student_id   int                                  not null,
    mission_id   int                                  not null,
    description  text                                 null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    primary key (student_id, mission_id),
    constraint missingreport_ibfk_1
        foreign key (student_id) references student (id),
    constraint missingreport_ibfk_2
        foreign key (mission_id) references mission (id)
);

create index mission_id
    on missingreport (mission_id);

create table registermission
(
    student_id   int                                  not null,
    mission_id   int                                  not null,
    is_completed tinyint(1) default 0                 null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    primary key (student_id, mission_id),
    constraint registermission_ibfk_1
        foreign key (student_id) references student (id),
    constraint registermission_ibfk_2
        foreign key (mission_id) references mission (id)
);

create index mission_id
    on registermission (mission_id);

create index class_id
    on student (class_id);

create index faculty_id
    on student (faculty_id);


