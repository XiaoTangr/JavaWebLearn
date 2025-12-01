package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.UserEntity;

/**
 * 数据备份控制器接口
 * 定义数据备份相关操作的接口，用于处理数据导出、备份和恢复等逻辑
 */
public interface DataBackupController {
    
    void setCurrentUser(UserEntity user);

    UserEntity getCurrentUser();

    /**
     * 启动数据备份模块
     */
    void startUp();

    /**
     * 管理员面板
     */
    void adminPanel();
}