package cn.javat.javaLearn.experiment4.service;

import cn.javat.javaLearn.experiment4.entity.CouponEntity;
import java.util.List;

/**
 * 优惠券服务接口
 */
public interface CouponService {
    /**
     * 发放优惠券
     * @param coupon 优惠券对象
     * @return 是否发放成功
     */
    boolean issueCoupon(CouponEntity coupon);
    
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
     * 使用优惠券
     * @param couponId 优惠券ID
     * @return 是否使用成功
     */
    boolean useCoupon(Long couponId);
    
    /**
     * 核销优惠券
     * @param couponCode 优惠券代码
     * @return 是否核销成功
     */
    boolean redeemCoupon(String couponCode);
}