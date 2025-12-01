package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.dao.Impl.CartDaoImpl;
import cn.javat.javaLearn.experiment4.entity.CartItemEntity;
import cn.javat.javaLearn.experiment4.service.CartService;

import java.util.ArrayList;

public class CartServiceImpl implements CartService {
    
    private final CartDaoImpl cartDao = new CartDaoImpl();
    
    // 使用 volatile 关键字确保多线程环境下的可见性
    private static volatile CartServiceImpl instance;

    private CartServiceImpl() {
    }

    public static CartServiceImpl getInstance() {
        // 双重检查锁定模式
        if (instance == null) {
            synchronized (CartServiceImpl.class) {
                if (instance == null) {
                    instance = new CartServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public int addToCart(long userId, long vehicleId, int quantity) {
        // 检查购物车中是否已存在该商品
        CartItemEntity existingItem = cartDao.selectByUserAndVehicle(userId, vehicleId);
        if (existingItem != null) {
            // 如果存在，则增加数量
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUpdateTime(System.currentTimeMillis());
            return cartDao.update(existingItem) > 0 ? 0 : -1;
        } else {
            // 如果不存在，则添加新商品
            CartItemEntity newItem = CartItemEntity.builder()
                    .userId(userId)
                    .vehicleId(vehicleId)
                    .quantity(quantity)
                    .createTime(System.currentTimeMillis())
                    .updateTime(System.currentTimeMillis())
                    .build();
            return cartDao.insert(newItem) > 0 ? 0 : -1;
        }
    }

    @Override
    public int updateQuantity(long userId, long vehicleId, int quantity) {
        CartItemEntity existingItem = cartDao.selectByUserAndVehicle(userId, vehicleId);
        if (existingItem != null) {
            if (quantity <= 0) {
                // 如果数量小于等于0，则删除该项目
                return cartDao.delete(existingItem.getId()) > 0 ? 0 : -1;
            } else {
                // 更新数量
                existingItem.setQuantity(quantity);
                existingItem.setUpdateTime(System.currentTimeMillis());
                return cartDao.update(existingItem) > 0 ? 0 : -1;
            }
        } else {
            return -1; // 项目不存在
        }
    }

    @Override
    public int removeFromCart(long userId, long vehicleId) {
        CartItemEntity existingItem = cartDao.selectByUserAndVehicle(userId, vehicleId);
        if (existingItem != null) {
            return cartDao.delete(existingItem.getId()) > 0 ? 0 : -1;
        } else {
            return -1; // 项目不存在
        }
    }

    @Override
    public ArrayList<CartItemEntity> getCartItems(long userId) {
        return cartDao.selectByUserId(userId);
    }

    @Override
    public int clearCart(long userId) {
        return cartDao.clearCart(userId) >= 0 ? 0 : -1;
    }
}