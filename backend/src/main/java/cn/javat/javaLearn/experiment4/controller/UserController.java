package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.UserEntity;

import java.util.ArrayList;

/**
 * 用户控制器接口
 * 定义用户相关操作的接口，用于处理用户身份验证、用户信息管理等逻辑
 */
public interface UserController {

    /**
     * 启动程序入口点
     */
    void startUp(); // 启动

    //    —————————————————— 普通用户 ——————————————————————

    /**
     * 普通用户面板界面
     * 提供普通用户的功能选项菜单
     */
    void userPanel(); // 用户面板

    //    —————————————————— 管 理 员 ——————————————————————

    /**
     * 管理员面板界面
     * 提供管理员的高级功能选项菜单
     */
    void adminPanel();  // 管理员面板

    //    —————————————————— 公    共 ——————————————————————

    /**
     * 用户登录功能
     * 处理用户的身份验证过程
     */
    void login(); // 登录

    /**
     * 用户注册功能
     * 处理新用户的注册流程
     */
    void register(); // 注册

    /**
     * 用户登出功能
     * 处理用户的登出操作并清理会话数据
     */
    void logout(); // 登出

    /**
     * 更新指定用户的信息
     *
     * @param user 需要更新的用户实体对象
     */
    void updateUser(UserEntity user);

    /**
     * 管理员更新用户信息功能
     * 允许管理员修改任意用户的信息
     */
    void updateUserWithAdmin();

    /**
     * 打印用户详细信息
     *
     * @param user 用户实体对象
     */
    void printUser(UserEntity user);

    /**
     * 以内联格式打印用户信息
     *
     * @param user 用户实体对象
     */
    void printUserInline(UserEntity user);

    /**
     * 打印所有用户信息
     *
     * @param users 用户列表
     */
    void printAllUser(ArrayList<UserEntity> users);
}