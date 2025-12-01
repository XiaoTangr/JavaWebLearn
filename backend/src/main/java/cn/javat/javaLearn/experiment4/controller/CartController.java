package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.UserEntity;

/**
 * 购物车控制器接口
 * 定义购物车相关操作的接口，用于处理购物车管理逻辑
 */
public interface CartController {
    
    void setCurrentUser(UserEntity user);

    UserEntity getCurrentUser();

    /**
     * 启动购物车模块
     */
    void startUp();

    /**
     * 用户购物车面板
     */
    void userPanel();
}