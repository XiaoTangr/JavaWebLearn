package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.OrderEntity;

import java.util.ArrayList;

public interface OrderDao {
    int insert(OrderEntity order);

    int update(OrderEntity order);

    int delete(long pk);

    OrderEntity select(long pk);

    ArrayList<OrderEntity> selectAll();
    
    ArrayList<OrderEntity> selectByUserId(long userId);

    ArrayList<OrderEntity> selectByVehicleId(long vehicleId);
}