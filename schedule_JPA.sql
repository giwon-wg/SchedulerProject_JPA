CREATE DATABASE scheduler_jpa;

create table schedule(
                         id bigint auto_increment primary key comment '일정 고유 ID',
                         user VARCHAR(50) not null comment '작성자 이름',
                         title VARCHAR(50) not null comment '일정 제목',
                         todo TEXT not null comment '일정 내용',
                         created_at DATETIME default current_timestamp comment '작성일',
                         modified_at DATETIME default current_timestamp on update current_timestamp comment '수정일'
) comment '일정(schedule) 테이블'