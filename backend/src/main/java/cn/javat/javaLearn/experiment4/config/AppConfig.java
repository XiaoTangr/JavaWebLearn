package cn.javat.javaLearn.experiment4.config;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置类
 * 单例模式设计，配置全局共享化
 */
public class AppConfig {
    private static volatile AppConfig instance;
    private final Properties appConfig;

    public AppConfig() {
        appConfig = new Properties();
        try {
            appConfig.load(this.getClass().getClassLoader().getResourceAsStream("Config.Properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取单例
     *
     * @return AppConfig
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 获取配置项
     *
     * @param key 配置项key
     * @return 配置项value
     */
    public String getProperty(String key) {
        return appConfig.getProperty(key);
    }

    /**
     * 获取配置项
     *
     * @param key          配置项key
     * @param defaultValue 默认值
     * @return 配置项value
     */
    public String getProperty(String key, String defaultValue) {
        return appConfig.getProperty(key, defaultValue);
    }

    /**
     * 设置配置项
     *
     * @param key   配置项key
     * @param value 配置项value
     */
    public void setProperty(String key, String value) {
        appConfig.setProperty(key, value);
    }
}