package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.CouponEntity;
import java.util.List;

/**
 * 优惠券DAO接口
 */
public interface CouponDao {
    /**
     * 创建优惠券
     * @param coupon 优惠券对象
     * @return 是否创建成功
     */
    boolean createCoupon(CouponEntity coupon);
    
    /**
     * 根据ID获取优惠券
     * @param id 优惠券ID
     * @return 优惠券对象
     */
    CouponEntity getCouponById(Long id);
    
    /**
     * 根据代码获取优惠券
     * @param code 优惠券代码
     * @return 优惠券对象
     */
    CouponEntity getCouponByCode(String code);
    
    /**
     * 更新优惠券
     * @param coupon 优惠券对象
     * @return 是否更新成功
     */
    boolean updateCoupon(CouponEntity coupon);
    
    /**
     * 删除优惠券
     * @param id 优惠券ID
     * @return 是否删除成功
     */
    boolean deleteCoupon(Long id);
    
    /**
     * 获取所有有效的优惠券
     * @return 优惠券列表
     */
    List<CouponEntity> getAllValidCoupons();
    
    /**
     * 获取所有优惠券
     * @return 优惠券列表
     */
    List<CouponEntity> getAllCoupons();
}