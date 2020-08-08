package com.kaiqiang.simple.mq.server.store;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.kaiqiang.simple.mq.api.CommonMessage;
import com.kaiqiang.simple.mq.api.Message;
import com.kaiqiang.simple.mq.server.util.LocalCache;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class MysqlServerStore implements ServerStore {

    private static final DataSource dataSource;
    private static final QueryRunner runner;

    private static final String DEFAULT_DRUID_FILE = "druid.properties";
    static {
        try {
            InputStream in = MysqlServerStore.class.getClassLoader().getResourceAsStream(DEFAULT_DRUID_FILE);
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

    private LocalCache<String, String> insertSqlCache = new LocalCache<>(t ->
            "insert into #{tableName}(message_id, create_time, attr) value(?, ?, ?)".replaceFirst("#\\{tableName}", t));
    @Override
    public void storeMessage(Message message, String tableName) throws SQLException {
        Object[] params = {
            message.getMessageId(), message.getCreateTime(), message.storeAttr()
        };
        runner.update(
                insertSqlCache.getValue(tableName),
                params
        );
    }

    // TODO: 2020/8/8  Test
    public static void main(String[] args) throws SQLException {
        ServerStore store = new MysqlServerStore();
        Message message = new CommonMessage(System.currentTimeMillis(), "app.order.finished", LocalDateTime.now());
        message.setProperty("orderNo", "tieyou-" + System.currentTimeMillis());
        message.setProperty("userName", "apple");
        store.storeMessage(message, "message_store_0001");
    }
}
