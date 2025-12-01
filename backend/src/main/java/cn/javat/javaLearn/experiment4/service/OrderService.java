package cn.javat.javaLearn.experiment4.service;

import cn.javat.javaLearn.experiment4.entity.OrderEntity;

import java.util.ArrayList;

public interface OrderService {

    /**
     * 根据用户ID查询订单
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    ArrayList<OrderEntity> selectByUserId(long userId);

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    OrderEntity select(long orderId);

    /**
     * 插入订单
     *
     * @param order 订单
     * @return 插入结果 0: 插入成功 -1: 插入失败
     */
    int insert(OrderEntity order);

    /**
     * 更新订单
     *
     * @param order 订单
     * @return 更新结果 0: 更新成功 -1: 更新失败
     */
    int update(OrderEntity order);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 删除结果 0: 删除成功 -1: 删除失败
     */
    int delete(long orderId);

    /**
     * 查询所有订单
     *
     * @return 所有订单
     */
    ArrayList<OrderEntity> selectAll();

    /**
     * 根据车辆ID查询订单
     *
     * @param vehicleId 车辆ID
     * @return 订单列表
     */
    ArrayList<OrderEntity> selectByVehicleId(long vehicleId);
};
