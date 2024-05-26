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

create table point_group
(
    id        int auto_increment
        primary key,
    name      varchar(255) not null,
    content   text         null,
    max_point int          null,
    constraint id_UNIQUE
        unique (id)
);

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
    role       enum ('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_ASSISTANT') null,
    constraint user_pk
        unique (username, email, phone)
);

create table activity
(
    id            int auto_increment
        primary key,
    name          varchar(255)                         not null,
    pointgroup_id int                                  null,
    is_active     tinyint(1) default 1                 null,
    created_date  timestamp  default CURRENT_TIMESTAMP null,
    max_point     int                                  null,
    updated_date  timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    assistant_id  int                                  null,
    constraint activity_ibfk_1
        foreign key (pointgroup_id) references point_group (id),
    constraint activity_user_id_fk
        foreign key (assistant_id) references user (id)
            on update cascade on delete cascade
);

create index pointgroup_id
    on activity (pointgroup_id);

create table assistant
(
    id         int not null
        primary key,
    faculty_id int null,
    constraint assistant_ibfk_1
        foreign key (id) references user (id)
            on update cascade,
    constraint assistant_ibfk_2
        foreign key (faculty_id) references faculty (id)
);

create index faculty_id
    on assistant (faculty_id);

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
            on delete cascade
);

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
    constraint student_id_UNIQUE
        unique (student_id),
    constraint student_ibfk_1
        foreign key (id) references user (id)
            on delete cascade,
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

create table missing_report
(
    description  text                                 null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    id           int auto_increment
        primary key,
    student_id   int                                  not null,
    mission_id   int                                  not null,
    constraint missing_report_mission_id_fk
        foreign key (mission_id) references mission (id),
    constraint missing_report_student_id_fk
        foreign key (student_id) references student (id)
);

create table reaction
(
    id           int auto_increment
        primary key,
    student_id   int                                  null,
    post_id      int                                  null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint reaction_ibfk_1
        foreign key (student_id) references student (id),
    constraint reaction_ibfk_2
        foreign key (post_id) references post (id)
);

create index post_id
    on reaction (post_id);

create index student_id
    on reaction (student_id);

create table register_mission
(
    student_id   int                                  not null,
    mission_id   int                                  not null,
    is_completed tinyint(1) default 0                 null,
    is_active    tinyint(1) default 1                 null,
    created_date timestamp  default CURRENT_TIMESTAMP null,
    updated_date timestamp  default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    id           int auto_increment
        primary key,
    constraint register_mission_mission_id_fk
        foreign key (mission_id) references mission (id),
    constraint register_mission_student_id_fk
        foreign key (student_id) references student (id)
);

create index class_id
    on student (class_id);

create index faculty_id
    on student (faculty_id);

create
    definer = root@localhost procedure CalculateStudentPoints()
BEGIN
    -- Temporary table to store missions points per activity
    CREATE TEMPORARY TABLE IF NOT EXISTS missions_point_per_activity AS
    SELECT s.id, m.activity_id AS activity_id, SUM(m.point) AS missions_point
    FROM registermission
    JOIN training_point.mission m ON m.id = registermission.mission_id
    JOIN training_point.student s ON s.id = registermission.student_id
    GROUP BY s.id, m.activity_id;

    -- Temporary table to store total points per activity
    CREATE TEMPORARY TABLE IF NOT EXISTS total_point_per_activity AS
    SELECT mppa.id,
           p.id AS pointgroup_id,
           SUM(IF(mppa.missions_point > a.max_point, a.max_point, mppa.missions_point)) AS activty_point
    FROM missions_point_per_activity AS mppa
    JOIN activity a ON mppa.activity_id = a.id
    JOIN training_point.pointgroup p ON a.pointgroup_id = p.id
    GROUP BY mppa.id, pointgroup_id;

    -- Temporary table to store total points per point group
    CREATE TEMPORARY TABLE IF NOT EXISTS total_point_per_pg AS
    SELECT tppa.id, 
           SUM(IF(tppa.activty_point > p.max_point, p.max_point, tppa.activty_point)) AS total_point
    FROM total_point_per_activity AS tppa
    JOIN pointgroup p ON tppa.pointgroup_id = p.id
    GROUP BY tppa.id;

    -- Final result selection
    SELECT s.id, s.student_id, u.first_name, u.last_name, c.name, f.name,
           COALESCE(SUM(tppp.total_point), 0) AS total_points
    FROM student s
    LEFT JOIN total_point_per_pg AS tppp ON s.id = tppp.id
    join user as u on u.id = s.id
    join class as c on c.id = s.class_id
    join faculty as f on f.id = s.faculty_id
    GROUP BY s.id;

    -- Drop temporary tables
    DROP TEMPORARY TABLE IF EXISTS missions_point_per_activity;
    DROP TEMPORARY TABLE IF EXISTS total_point_per_activity;
    DROP TEMPORARY TABLE IF EXISTS total_point_per_pg;
END;


