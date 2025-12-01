package cn.javat.javaLearn.experiment4.utils;

import cn.javat.javaLearn.experiment4.config.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final DBUtils INSTANCE = new DBUtils();
    private final AppConfig appConfig = AppConfig.getInstance();
    private boolean driverLoaded = false;

    private DBUtils() {
    }

    // 获取单例实例
    public static DBUtils getInstance() {
        return INSTANCE;
    }

    // 加载数据库驱动
    private void loadDriver() {
        if (!driverLoaded) {
            try {
                Class.forName(appConfig.getProperty("database.driver"));
                driverLoaded = true;
            } catch (ClassNotFoundException e) {
                AppUtils.print("数据库驱动加载失败: " + e);
            }
        }
    }

    // 获取数据库连接
    public Connection getConnection() {
        loadDriver();
        if (!driverLoaded) {
            AppUtils.print("数据库驱动未正确加载，无法建立连接");
            return null;
        }
        try {
            return DriverManager.getConnection(
                    appConfig.getProperty("database.url"),
                    appConfig.getProperty("database.username"),
                    appConfig.getProperty("database.password")
            );
        } catch (SQLException e) {
            AppUtils.print("数据库连接失败，请检查数据库配置: " + e);
            return null;
        }
    }
    
}
