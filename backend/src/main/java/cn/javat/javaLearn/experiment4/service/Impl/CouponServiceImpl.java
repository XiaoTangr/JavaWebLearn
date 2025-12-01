package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.dao.CouponDao;
import cn.javat.javaLearn.experiment4.dao.Impl.CouponDaoImpl;
import cn.javat.javaLearn.experiment4.entity.CouponEntity;
import cn.javat.javaLearn.experiment4.service.CouponService;

import java.util.List;

/**
 * 优惠券服务实现类
 */
public class CouponServiceImpl implements CouponService {
    
    private CouponDao couponDao;
    
    public CouponServiceImpl() {
        this.couponDao = new CouponDaoImpl();
    }
    
    @Override
    public boolean issueCoupon(CouponEntity coupon) {
        // 检查优惠券是否有效
        if (coupon == null || coupon.getCode() == null || coupon.getCode().isEmpty()) {
            return false;
        }
        
        // 设置默认值
        if (coupon.getIsActive() == null) {
            coupon.setIsActive(true);
        }
        
        return couponDao.createCoupon(coupon);
    }
    
    @Override
    public CouponEntity getCouponById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        
        return couponDao.getCouponById(id);
    }
    
    @Override
    public CouponEntity getCouponByCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        
        return couponDao.getCouponByCode(code);
    }
    
    @Override
    public boolean updateCoupon(CouponEntity coupon) {
        if (coupon == null || coupon.getId() == null || coupon.getId() <= 0) {
            return false;
        }
        
        return couponDao.updateCoupon(coupon);
    }
    
    @Override
    public boolean deleteCoupon(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        
        return couponDao.deleteCoupon(id);
    }
    
    @Override
    public List<CouponEntity> getAllValidCoupons() {
        return couponDao.getAllValidCoupons();
    }
    
    @Override
    public boolean useCoupon(Long couponId) {
        if (couponId == null || couponId <= 0) {
            return false;
        }
        
        CouponEntity coupon = couponDao.getCouponById(couponId);
        if (coupon != null) {
            coupon.use();
            return couponDao.updateCoupon(coupon);
        }
        
        return false;
    }
    
    @Override
    public boolean redeemCoupon(String couponCode) {
        if (couponCode == null || couponCode.isEmpty()) {
            return false;
        }
        
        CouponEntity coupon = couponDao.getCouponByCode(couponCode);
        if (coupon != null && coupon.isValid()) {
            coupon.use();
            return couponDao.updateCoupon(coupon);
        }
        
        return false;
    }
}