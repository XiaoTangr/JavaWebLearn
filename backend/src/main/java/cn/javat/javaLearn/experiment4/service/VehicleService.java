package cn.javat.javaLearn.experiment4.service;

import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;

import java.util.ArrayList;

public interface VehicleService {
    /**
     * 查询车辆
     *
     * @param carId 车辆ID
     * @return 车辆
     */
    VehicleEntity selectVehicle(long carId);

    /**
     * 获取车辆数量
     *
     * @return 数量
     */
    ArrayList<VehicleEntity> selectAllVehicle();

    /**
     * 插入车辆
     *
     * @param vehicle 车辆
     * @return 插入结果 0: 插入成功 -1: 插入失败
     */
    int insertVehicle(VehicleEntity vehicle);

    /**
     * 更新车辆
     *
     * @param vehicle 车辆
     * @return 0 插入成功 -1 插入失败
     */
    int updateVehicle(VehicleEntity vehicle);

    /**
     * 删除车辆
     *
     * @param carId 车辆ID
     * @return 0: 删除成功 -1: 删除失败
     */
    int DeleteVehicle(long carId);

    /**
     * 购买车辆
     *
     * @param order 订单
     * @return 0: 购买成功 -1: 车辆不存在 -2：用户不存在 -3: 库存不足
     */
    int buyVehicle(OrderEntity order);
    
    /**
     * 根据关键词搜索车辆
     * 
     * @param keyword 关键词
     * @return 车辆列表
     */
    ArrayList<VehicleEntity> searchByKeyword(String keyword);
    
    /**
     * 根据条件搜索车辆
     * 
     * @param brand 品牌
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param type 类型
     * @return 车辆列表
     */
    ArrayList<VehicleEntity> searchByCondition(String brand, Double minPrice, Double maxPrice, String type);
    
    /**
     * 获取低库存车辆
     * 
     * @param threshold 库存阈值
     * @return 低库存车辆列表
     */
    ArrayList<VehicleEntity> getLowStockVehicles(int threshold);
    
    /**
     * 计算库存周转率
     * 
     * @return 库存周转率
     */
    double calculateInventoryTurnoverRate();
}