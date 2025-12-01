package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;

import java.util.ArrayList;

/**
 * 车辆控制器接口
 * <p>
 * 定义车辆相关操作的接口，用于处理车辆管理、购买及相关信息展示逻辑
 * 支持普通用户购买车辆和管理员管理车辆信息的功能
 * </p>
 */
public interface VehicleController {
    /**
     * 启动车辆管理系统入口点
     * 根据当前用户角色决定进入普通用户面板还是管理员面板
     */
    void startUp();

    //    —————————————————— 普通用户 ——————————————————————
    
    /**
     * 普通用户车辆管理面板
     * 提供普通用户购买车辆和查询订单等功能
     */
    void userPanel();

    /**
     * 购买车辆功能
     * 处理用户选择车辆、输入购买数量、生成订单等购买流程
     */
    void buyVehicle(); // 购买车辆

    //    —————————————————— 管 理 员 ——————————————————————

    /**
     * 管理员车辆管理面板
     * 提供管理员添加、删除、修改车辆信息等管理功能
     */
    void adminPanel();  // 管理员面板

    /**
     * 添加新车辆
     * 支持添加乘用车或商用车，并设置相应参数
     */
    void addVehicle(); // 添加车辆

    /**
     * 删除车辆
     * 根据车辆ID删除指定车辆信息
     */
    void deleteVehicle();

    /**
     * 更新车辆信息
     * 支持修改车辆价格、库存及车型特有属性等信息
     */
    void updateVehicle();

    /**
     * 搜索车辆功能
     * 提供关键词搜索和条件筛选功能
     */
    void searchVehicle();
    
    /**
     * 库存预警功能
     * 显示低于指定阈值的车辆
     */
    void showLowStockVehicles();
    
    /**
     * 库存统计功能
     * 显示库存周转率等统计信息
     */
    void showInventoryStatistics();

    //    —————————————————— 公    共 ——————————————————————

    /**
     * 打印车辆详细信息
     *
     * @param vehicle 车辆实体对象
     */
    void printVehicle(VehicleEntity vehicle);

    /**
     * 以内联格式打印车辆信息
     *
     * @param vehicle 车辆实体对象
     */
    void printVehicleInline(VehicleEntity vehicle);

    /**
     * 打印所有车辆信息
     *
     * @param vehicles 车辆列表
     */
    void printAllVehicle(ArrayList<VehicleEntity> vehicles);

    // setter and getter for currentUser
    
    /**
     * 设置当前用户
     *
     * @param user 当前登录的用户实体对象
     */
    void setCurrentUser(UserEntity user);

    /**
     * 获取当前用户
     *
     * @return 当前登录的用户实体对象
     */
    UserEntity getCurrentUser();
}