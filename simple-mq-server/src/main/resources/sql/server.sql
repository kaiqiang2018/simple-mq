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