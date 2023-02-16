package cn.asedu.dynamic_rule.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author ~~
 * @version 1.0
 * @desc 各类外部链接创建工具类
 */
@Slf4j
public class ConnectionUtils {


    public static Connection getClickhouseConnection() throws Exception {
        //String ckDriver = "com.github.housepower.jdbc.ClickHouseDriver";
        String ckDriver = "ru.yandex.clickhouse.ClickHouseDriver";
        String ckUrl = "jdbc:clickhouse://node03:8123/default";
        String table = "zenniu_detail";

        Class.forName(ckDriver);
        Connection conn = DriverManager.getConnection(ckUrl);
        log.debug("clickhouse jdbc 连接创建完成");
        return conn;
    }


}
