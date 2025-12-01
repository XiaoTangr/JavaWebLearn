package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.UserEntity;

/**
 * 客户服务控制器接口
 * 定义客户服务相关操作的接口，用于处理客户咨询、投诉和评价等逻辑
 */
public interface CustomerServiceController {
    
    void setCurrentUser(UserEntity user);

    UserEntity getCurrentUser();

    /**
     * 启动客户服务模块
     */
    void startUp();

    /**
     * 用户面板
     */
    void userPanel();

    /**
     * 管理员面板
     */
    void adminPanel();
}