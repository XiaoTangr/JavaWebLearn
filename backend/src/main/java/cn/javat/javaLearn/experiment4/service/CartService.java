package cn.javat.javaLearn.experiment4.service;

import cn.javat.javaLearn.experiment4.entity.CartItemEntity;

import java.util.ArrayList;

public interface CartService {
    /**
     * 添加商品到购物车
     * @param userId 用户ID
     * @param vehicleId 车辆ID
     * @param quantity 数量
     * @return 添加结果 0: 成功 -1: 失败
     */
    int addToCart(long userId, long vehicleId, int quantity);

    /**
     * 更新购物车商品数量
     * @param userId 用户ID
     * @param vehicleId 车辆ID
     * @param quantity 数量
     * @return 更新结果 0: 成功 -1: 失败
     */
    int updateQuantity(long userId, long vehicleId, int quantity);

    /**
     * 从购物车移除商品
     * @param userId 用户ID
     * @param vehicleId 车辆ID
     * @return 删除结果 0: 成功 -1: 失败
     */
    int removeFromCart(long userId, long vehicleId);

    /**
     * 获取用户购物车中的所有商品
     * @param userId 用户ID
     * @return 购物车项目列表
     */
    ArrayList<CartItemEntity> getCartItems(long userId);

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 清空结果 0: 成功 -1: 失败
     */
    int clearCart(long userId);
}