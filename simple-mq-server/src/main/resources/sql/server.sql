use simple_mq_server;

/*
1 subject -> n message table (提高堆积能力)
1 subject -> n consumer (客观存在)

记录message table中当前已经分发的id、对应的subject、
*/

/* todo 不应在message_id上添加唯一索引, 因为消费端重复发送的情况非常少 */
DROP TABLE IF EXISTS message_store;
CREATE TABLE message_store
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_id     BIGINT UNSIGNED   NOT NULL COMMENT 'messageId',
    create_time    DATETIME       NOT NULL COMMENT '创建时间',
    attr           VARCHAR(10240) not null COMMENT 'attr',

    PRIMARY KEY (id),
    KEY idx_message_id (message_id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='server端消息持久化表';


DROP TABLE IF EXISTS subject_message_meta;
CREATE TABLE subject_message_meta
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    subject        VARCHAR(64)    not null COMMENT 'subject',
    message_store_table_name    varchar(64)  not null comment '存储消息的表名',
    consume_queue_delivery_id BIGINT UNSIGNED comment '当前table_name已经分发的id, 初始情况下为NULL',

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='主题相关信息存储表';

/* ------------------------------------------------------------------------ */
/**/
/**/
/**/
DROP TABLE IF EXISTS message_consume_queue_meta;
CREATE TABLE message_consume_queue_meta
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    subject_message_meta_id BIGINT UNSIGNED   NOT NULL,
    consume_group VARCHAR(64)    not null,
    consume_queue_table_name varchar(64)  not null,
    consume_queue_pull_id BIGINT UNSIGNED,

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='message_consume_queue_meta 表';


DROP TABLE IF EXISTS consume_queue;
CREATE TABLE consume_queue
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_store_id BIGINT UNSIGNED   NOT NULL comment 'message_store id',
    pull_count     tinyint not null default 0 comment 'client端pull次数',
    can_pull_time  DATETIME NOT NULL COMMENT 'client端可以pull该消息的时间',
    state           tinyint not null comment '当前状态：1 可以被发送, 2 死信',

    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='consume queue 表';



