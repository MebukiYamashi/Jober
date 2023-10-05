create table member
(
    id         bigint primary key auto_increment,
    email      varchar(30)  not null,
    password   varchar(100) not null,
    username   varchar(10)  not null,
    created_at datetime     not null DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table template
(
    id                  bigint primary key auto_increment,
    title               varchar(30)  not null,
    description         varchar(100) not null,
    hashtag             varchar(100),
    thumbnail_image_url varchar(200),
    created_at          datetime     not null DEFAULT CURRENT_TIMESTAMP,
    updated_at          datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table template_type
(
    id          bigint primary key auto_increment,
    template_id bigint      not null,
    type        varchar(20) not null,
    created_at  datetime    not null DEFAULT CURRENT_TIMESTAMP,
    updated_at  datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key (template_id) references template (id)
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table my_template
(
    id          bigint primary key auto_increment,
    member_id   bigint   not null,
    template_id bigint   not null,
    created_at  datetime not null DEFAULT CURRENT_TIMESTAMP,
    updated_at  datetime          DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table template_used_history
(
    id          bigint primary key auto_increment,
    member_id   bigint not null,
    template_id bigint not null,
    used_at     datetime DEFAULT CURRENT_TIMESTAMP
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_wall
(
    id                   bigint primary key auto_increment,
    url                  varchar(100),
    title                varchar(100),
    description          varchar(100),
    profile_image_url    varchar(255),
    background_image_url varchar(255),
    path_ids             varchar(100),
    sequence             int      not null,
    authorized           boolean  not null default false,
    parent_space_wall_id bigint,
    create_member_id     bigint   not null,
    created_at           datetime not null DEFAULT CURRENT_TIMESTAMP,
    updated_at           datetime          DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key (create_member_id) references member (id)
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table component
(
    id                   bigint primary key auto_increment,
    parent_space_wall_id bigint      not null,
    template_id          bigint,
    this_space_wall_id   bigint,
    type                 varchar(20) not null,
    visible              boolean     not null,
    title                varchar(100),
    content              varchar(1000),
    sequence             int         not null,
    created_at           datetime    not null DEFAULT CURRENT_TIMESTAMP,
    updated_at           datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key (parent_space_wall_id) references space_wall (id),
    foreign key (template_id) references template (id),
    foreign key (this_space_wall_id) references space_wall (id)
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_wall_member
(
    id            bigint primary key auto_increment,
    member_id     bigint   not null,
    space_wall_id bigint   not null,
    created_at    datetime not null DEFAULT CURRENT_TIMESTAMP,
    updated_at    datetime          DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key (member_id) references member (id),
    foreign key (space_wall_id) references space_wall (id)
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_wall_permission
(
    id bigint primary key auto_increment,
    space_wall_member_id bigint not null,
    auths varchar(20) default 'VIEWER',
    parent_id bigint,
    created_at datetime not null DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key(space_wall_member_id) references space_wall_member(id) on delete CASCADE
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_wall_history
(
    id                   bigint primary key auto_increment,
    space_wall_id        biginy,
    url                  varchar(100),
    title                varchar(100),
    description          varchar(100),
    profile_image_url    varchar(255),
    background_image_url varchar(255),
    path_ids             varchar(100),
    authorized           boolean,
    sequence             int,
    parent_space_wall_id bigint,
    create_member_id     bigint   not null,
    created_at           datetime not null DEFAULT CURRENT_TIMESTAMP,
    updated_at           datetime          DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table component_history
(
    id                    bigint primary key auto_increment,
    space_wall_history_id bigint      not null,
    template_id           bigint,
    this_space_wall_id    bigint,
    type                  varchar(20) not null,
    visible               boolean     not null,
    title                 varchar(100),
    content               varchar(1000),
    sequence              int         not null,
    parent_space_wall_id  bigint,
    created_at            datetime    not null DEFAULT CURRENT_TIMESTAMP,
    updated_at            datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table space_wall_temp
(
    id                   bigint primary key auto_increment,
    space_wall_id        bigint   not null,
    title                varchar(100),
    description          varchar(100),
    profile_image_url    varchar(255),
    background_image_url varchar(255),
    sequence             int      not null,
    created_at           datetime not null DEFAULT CURRENT_TIMESTAMP,
    updated_at           datetime          DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key (space_wall_id) references space_wall (id)
)engine=InnoDB DEFAULT CHARSET=utf8mb4;

create table component_temp
(
    id                        bigint primary key auto_increment,
    parent_space_wall_temp_id bigint      not null,
    this_space_wall_id        bigint,
    template_id               bigint,
    component_id              bigint,
    type                      varchar(20) not null,
    visible                   boolean     not null,
    title                     varchar(100),
    content                   varchar(1000),
    sequence                  int         not null,
    deleted                   boolean,
    created_at                datetime    not null DEFAULT CURRENT_TIMESTAMP,
    updated_at                datetime             DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    foreign key (parent_space_wall_temp_id) references space_wall_temp (id),
    foreign key (this_space_wall_id) references space_wall (id),
    foreign key (template_id) references template (id)
)engine=InnoDB DEFAULT CHARSET=utf8mb4;