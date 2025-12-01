package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.controller.PromotionController;
import cn.javat.javaLearn.experiment4.entity.PromotionEntity;
import cn.javat.javaLearn.experiment4.service.PromotionService;
import cn.javat.javaLearn.experiment4.service.Impl.PromotionServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * 促销活动控制器实现类
 */
public class PromotionControllerImpl implements PromotionController {
    
    private PromotionService promotionService;
    
    public PromotionControllerImpl() {
        this.promotionService = new PromotionServiceImpl();
    }
    
    @Override
    public boolean createPromotion(PromotionEntity promotion) {
        try {
            return promotionService.createPromotion(promotion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public PromotionEntity getPromotionById(Long id) {
        try {
            return promotionService.getPromotionById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean updatePromotion(PromotionEntity promotion) {
        try {
            return promotionService.updatePromotion(promotion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deletePromotion(Long id) {
        try {
            return promotionService.deletePromotion(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<PromotionEntity> getAllValidPromotions() {
        try {
            return promotionService.getAllValidPromotions();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public BigDecimal applyPromotionToPrice(Long promotionId, BigDecimal originalPrice) {
        try {
            return promotionService.applyPromotionToPrice(promotionId, originalPrice);
        } catch (Exception e) {
            e.printStackTrace();
            return originalPrice;
        }
    }
    
    @Override
    public boolean startFlashSale(String name, BigDecimal discount, int durationHours) {
        try {
            return promotionService.startFlashSale(name, discount, durationHours);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean startFullReduction(String name, BigDecimal minPurchase, BigDecimal discount, int durationDays) {
        try {
            return promotionService.startFullReduction(name, minPurchase, discount, durationDays);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}