drop database simple_mq_server;
create database simple_mq_server;
use simple_mq_server;

/*
1 subject -> n message table (提高堆积能力)
1 subject -> n consumer (客观存在)

记录message table中当前已经分发的id、对应的subject、
*/

/* ------------------------------------------------------------------------------------------------------------------- */
/* todo 不应在message_id上添加唯一索引, 因为消费端重复发送的情况非常少 */
DROP TABLE IF EXISTS message_store_0001;
CREATE TABLE message_store_0001
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_id     BIGINT UNSIGNED   NOT NULL COMMENT 'messageId',
    create_time    DATETIME       NOT NULL COMMENT '创建时间',
    attr           VARCHAR(10240) not null COMMENT 'attr',

    PRIMARY KEY (id),
    KEY idx_message_id (message_id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='server端消息持久化表'
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS message_store_0002;
CREATE TABLE message_store_0002
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_id     BIGINT UNSIGNED   NOT NULL COMMENT 'messageId',
    create_time    DATETIME       NOT NULL COMMENT '创建时间',
    attr           VARCHAR(10240) not null COMMENT 'attr',

    PRIMARY KEY (id),
    KEY idx_message_id (message_id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='server端消息持久化表'
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS message_store_0003;
CREATE TABLE message_store_0003
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_id     BIGINT UNSIGNED   NOT NULL COMMENT 'messageId',
    create_time    DATETIME       NOT NULL COMMENT '创建时间',
    attr           VARCHAR(10240) not null COMMENT 'attr',

    PRIMARY KEY (id),
    KEY idx_message_id (message_id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='server端消息持久化表'
  AUTO_INCREMENT = 1;


/* --------------------------------------------------------------------------------- */
/**/
/**/
/**/
DROP TABLE IF EXISTS subject_message_store;
CREATE TABLE subject_message_store
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_store_table_name    varchar(64)  not null comment '存储消息的表名',
    subject        VARCHAR(64)    not null COMMENT 'subject',
    consume_queue_delivery_id BIGINT UNSIGNED not null comment '当前table_name已经分发的id, 初始情况下为NULL',

    PRIMARY KEY (id),
    unique uniq_message_store_table_name(message_store_table_name)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='主题相关信息存储表';

insert into
    subject_message_store(message_store_table_name, subject, consume_queue_delivery_id)
values
('message_store_0001', 'app.order.finished',  0),
('message_store_0002', 'app.order.finished',  0),
('message_store_0003', 'app.order.finished',  0);

select * from subject_message_store limit 100;

/* ------------------------------------------------------------------------ */
/**/
/**/
/**/
DROP TABLE IF EXISTS consumer_queue;
CREATE TABLE consumer_queue
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    consume_group VARCHAR(64)    not null,
    consume_queue_table_name varchar(64)  not null,
    consume_queue_pull_id BIGINT UNSIGNED not null,

    PRIMARY KEY (id),
    unique uniq_consume_queue_table_name(consume_queue_table_name)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='message_consume_queue_meta 表'
  auto_increment 10000;

insert into consumer_queue
(consume_group, consume_queue_table_name, consume_queue_pull_id)
values
('member', 'consume_queue_0001', 0),
('member', 'consume_queue_0002', 0),
('hotel', 'consume_queue_0003', 0),
('hotel', 'consume_queue_0004', 0);

select * from consumer_queue limit 100;


DROP TABLE IF EXISTS message_consumer;
CREATE TABLE message_consumer
(
    subject_message_store_id    BIGINT UNSIGNED   NOT NULL,
    consumer_queue_id BIGINT UNSIGNED   NOT NULL,

    PRIMARY KEY (subject_message_store_id, consumer_queue_id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='message_consume_queue_meta 表';

insert into message_consumer
values
(1, 10000),
(2, 10000),
(3, 10001),

(1, 10002),
(2, 10003),
(3, 10003);

select * from message_consumer limit 100;
/* ------------------------------------------------------------------------ */
/**/
/**/
/**/
DROP TABLE IF EXISTS consume_queue_0001;
CREATE TABLE consume_queue_0001
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_store_id BIGINT UNSIGNED   NOT NULL comment 'message_store id',
    pull_count     tinyint not null default 0 comment 'client端pull次数',
    can_pull_time  DATETIME NOT NULL COMMENT 'client端可以pull该消息的时间',
    state           tinyint not null comment '当前状态：1 可以被发送, 2 死信',

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='consume queue 表'
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS consume_queue_0002;
CREATE TABLE consume_queue_0002
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_store_id BIGINT UNSIGNED   NOT NULL comment 'message_store id',
    pull_count     tinyint not null default 0 comment 'client端pull次数',
    can_pull_time  DATETIME NOT NULL COMMENT 'client端可以pull该消息的时间',
    state           tinyint not null comment '当前状态：1 可以被发送, 2 死信',

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='consume queue 表'
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS consume_queue_0003;
CREATE TABLE consume_queue_0003
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_store_id BIGINT UNSIGNED   NOT NULL comment 'message_store id',
    pull_count     tinyint not null default 0 comment 'client端pull次数',
    can_pull_time  DATETIME NOT NULL COMMENT 'client端可以pull该消息的时间',
    state           tinyint not null comment '当前状态：1 可以被发送, 2 死信',

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='consume queue 表'
  AUTO_INCREMENT = 1;

DROP TABLE IF EXISTS consume_queue_0004;
CREATE TABLE consume_queue_0004
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_store_id BIGINT UNSIGNED   NOT NULL comment 'message_store id',
    pull_count     tinyint not null default 0 comment 'client端pull次数',
    can_pull_time  DATETIME NOT NULL COMMENT 'client端可以pull该消息的时间',
    state           tinyint not null comment '当前状态：1 可以被发送, 2 死信',

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='consume queue 表'
  AUTO_INCREMENT = 1;

select
    y.subject, y.message_store_table_name, x.consume_group, x.consume_queue_table_name
from consumer_queue x, subject_message_store y, message_consumer z
where x.id = z.consumer_queue_id and y.id = z.subject_message_store_id;