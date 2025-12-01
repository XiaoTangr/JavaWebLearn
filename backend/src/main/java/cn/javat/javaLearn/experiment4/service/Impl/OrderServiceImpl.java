package cn.javat.javaLearn.experiment4.service.Impl;


import cn.javat.javaLearn.experiment4.dao.Impl.OrderDaoImpl;
import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.service.OrderService;

import java.util.ArrayList;

public class OrderServiceImpl implements OrderService {
    
    // 使用 volatile 关键字确保多线程环境下的可见性
    private static volatile OrderServiceImpl instance;
    
    private final OrderDaoImpl orderDao = new OrderDaoImpl();

    // 私有构造函数，防止外部直接实例化
    private OrderServiceImpl() {
    }

    /**
     * 获取单例实例
     *
     * @return OrderServiceImpl 单例实例
     */
    public static OrderServiceImpl getInstance() {
        // 双重检查锁定模式
        if (instance == null) {
            synchronized (OrderServiceImpl.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 根据用户ID查询订单
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    @Override
    public ArrayList<OrderEntity> selectByUserId(long userId) {
        return orderDao.selectByUserId(userId);
    }

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    @Override
    public OrderEntity select(long orderId) {
        return orderDao.select(orderId);
    }

    /**
     * 插入订单
     *
     * @param order 订单
     * @return 插入结果 0: 插入成功 -1: 插入失败
     */
    @Override
    public int insert(OrderEntity order) {
        int result = orderDao.insert(order);
        return result > 0 ? 0 : -1;
    }

    /**
     * 更新订单
     *
     * @param order 订单
     * @return 更新结果 0: 更新成功 -1: 更新失败
     */
    @Override
    public int update(OrderEntity order) {
        int result = orderDao.update(order);
        return result > 0 ? 0 : -1;
    }

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 删除结果 0: 删除成功 -1: 删除失败
     */
    @Override
    public int delete(long orderId) {
        int result = orderDao.delete(orderId);
        return result > 0 ? 0 : -1;
    }

    /**
     * 查询所有订单
     *
     * @return 所有订单
     */
    @Override
    public ArrayList<OrderEntity> selectAll() {
        return orderDao.selectAll();
    }

    /**
     * 根据车辆ID查询订单
     *
     * @param vehicleId 车辆ID
     * @return 订单列表
     */
    @Override
    public ArrayList<OrderEntity> selectByVehicleId(long vehicleId) {
        return orderDao.selectByVehicleId(vehicleId);
    }
}