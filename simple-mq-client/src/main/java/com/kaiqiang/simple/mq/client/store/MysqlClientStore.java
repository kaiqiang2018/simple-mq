package com.kaiqiang.simple.mq.client.store;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.kaiqiang.simple.mq.api.ProductMessage;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

/**
 * @Author kaiqiang
 * @Date 2020/08/02
 */
public class MysqlClientStore implements ClientStore {

    private static final DataSource dataSource;
    private static final QueryRunner runner;

    private static final String DEFAULT_DRUID_FILE = "druid.properties";
    static {
        try {
            InputStream in = MysqlClientStore.class.getClassLoader().getResourceAsStream(DEFAULT_DRUID_FILE);
            if(in == null) {
                throw new RuntimeException("加载配置为null, config file = " + DEFAULT_DRUID_FILE);
            }
            Properties druidConfig = new Properties();
            druidConfig.load(in);
            dataSource = DruidDataSourceFactory.createDataSource(druidConfig);
            runner = new QueryRunner(dataSource);
        } catch (Exception e) {
            throw new RuntimeException("加载druid数据源失败", e);
        }
    }


    private static final String INSERT_TEMPLATE =
            "insert into simple_mq_message (message_id, subject, create_time, attr, send_count, next_send_time, state) values (?, ?, ?, ?, ?, ?, ?)";
    @Override
    public void storeMessage(ProductMessage message) throws SQLException {
        Object[] params = {
            message.getMessageId(), message.getSubject(), message.getCreateTime(), message.storeAttr(),
            message.getSendCount(), message.getNextSendTime(), message.getState()
        };
        runner.update(INSERT_TEMPLATE, params);
    }

    @Override
    public void deleteMessage(long messageId) {

    }

    @Override
    public void updateMessageStatus(long messageId, int newStatus) {

    }

    @Override
    public void updateSendCountAndRetryTime(long messageId, int sendCount, LocalDateTime nextRetryTime) throws SQLException {
        runner.update("update simple_mq_message set send_count = ?, next_send_time = ? where message_id = ?",
                sendCount, nextRetryTime, messageId);
    }

    @Override
    public List<ProductMessage> select(long pos, LocalDateTime startSendTime, int limit) {
        return null;
    }
}
