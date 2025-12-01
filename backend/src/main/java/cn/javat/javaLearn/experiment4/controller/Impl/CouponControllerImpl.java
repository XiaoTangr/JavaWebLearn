package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.controller.CouponController;
import cn.javat.javaLearn.experiment4.entity.CouponEntity;
import cn.javat.javaLearn.experiment4.service.CouponService;
import cn.javat.javaLearn.experiment4.service.Impl.CouponServiceImpl;

import java.util.List;

/**
 * 优惠券控制器实现类
 */
public class CouponControllerImpl implements CouponController {
    
    private CouponService couponService;
    
    public CouponControllerImpl() {
        this.couponService = new CouponServiceImpl();
    }
    
    @Override
    public boolean issueCoupon(CouponEntity coupon) {
        try {
            return couponService.issueCoupon(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public CouponEntity getCouponById(Long id) {
        try {
            return couponService.getCouponById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public CouponEntity getCouponByCode(String code) {
        try {
            return couponService.getCouponByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean updateCoupon(CouponEntity coupon) {
        try {
            return couponService.updateCoupon(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteCoupon(Long id) {
        try {
            return couponService.deleteCoupon(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<CouponEntity> getAllValidCoupons() {
        try {
            return couponService.getAllValidCoupons();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean useCoupon(Long couponId) {
        try {
            return couponService.useCoupon(couponId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean redeemCoupon(String couponCode) {
        try {
            return couponService.redeemCoupon(couponCode);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}