package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.CartItemEntity;

import java.util.ArrayList;

public interface CartDao {
    /**
     * 添加商品到购物车
     * @param cartItem 购物车项目
     * @return 添加结果 >0: 成功 -1: 失败
     */
    int insert(CartItemEntity cartItem);

    /**
     * 更新购物车项目
     * @param cartItem 购物车项目
     * @return 更新结果 >0: 成功 -1: 失败
     */
    int update(CartItemEntity cartItem);

    /**
     * 删除购物车项目
     * @param id 项目ID
     * @return 删除结果 >0: 成功 -1: 失败
     */
    int delete(long id);

    /**
     * 根据ID查询购物车项目
     * @param id 项目ID
     * @return 购物车项目
     */
    CartItemEntity select(long id);

    /**
     * 根据用户ID和车辆ID查询购物车项目
     * @param userId 用户ID
     * @param vehicleId 车辆ID
     * @return 购物车项目
     */
    CartItemEntity selectByUserAndVehicle(long userId, long vehicleId);

    /**
     * 查询用户购物车中的所有项目
     * @param userId 用户ID
     * @return 购物车项目列表
     */
    ArrayList<CartItemEntity> selectByUserId(long userId);

    /**
     * 清空用户购物车
     * @param userId 用户ID
     * @return 删除结果 >0: 成功 -1: 失败
     */
    int clearCart(long userId);
}