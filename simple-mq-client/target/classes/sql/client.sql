use simple_mq_client;

DROP TABLE IF EXISTS simple_mq_message;
CREATE TABLE simple_mq_message
(
    id             BIGINT UNSIGNED   NOT NULL AUTO_INCREMENT COMMENT 'id',
    message_id     BIGINT UNSIGNED   NOT NULL COMMENT 'messageId',
    subject        VARCHAR(64)    not null COMMENT 'subject',
    create_time    DATETIME       NOT NULL COMMENT '创建时间',
    attr           VARCHAR(10240) not null COMMENT 'attr',
    send_count     SMALLINT       not null COMMENT 'attr',
    next_send_time DATETIME       NOT NULL COMMENT '下一次发送时间',
    state          TINYINT        not null COMMENT '当前状态',

    PRIMARY KEY (id),
    UNIQUE KEY uniq_name (message_id),
    KEY idx_id_next_send_time_state (id, next_send_time, state)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8mb4 COMMENT ='simple-mq client端消息持久化表';

