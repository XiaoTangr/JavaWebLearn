package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.UserEntity;

/**
 * 订单控制器接口
 * 定义订单相关操作的接口，用于处理订单信息的展示逻辑
 */
public interface OrderController {

    void setCurrentUser(UserEntity user);

    UserEntity getCurrentUser();

    /**
     * 启动程序
     */
    void startUp();

}