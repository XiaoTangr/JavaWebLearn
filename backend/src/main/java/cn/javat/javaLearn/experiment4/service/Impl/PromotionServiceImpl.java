package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.dao.PromotionDao;
import cn.javat.javaLearn.experiment4.dao.Impl.PromotionDaoImpl;
import cn.javat.javaLearn.experiment4.entity.PromotionEntity;
import cn.javat.javaLearn.experiment4.service.PromotionService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 促销活动服务实现类
 */
public class PromotionServiceImpl implements PromotionService {
    
    private PromotionDao promotionDao;
    
    public PromotionServiceImpl() {
        this.promotionDao = new PromotionDaoImpl();
    }
    
    @Override
    public boolean createPromotion(PromotionEntity promotion) {
        // 检查促销活动参数
        if (promotion == null || promotion.getName() == null || promotion.getName().isEmpty()) {
            return false;
        }
        
        // 设置默认值
        if (promotion.getIsActive() == null) {
            promotion.setIsActive(true);
        }
        
        return promotionDao.createPromotion(promotion);
    }
    
    @Override
    public PromotionEntity getPromotionById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        
        return promotionDao.getPromotionById(id);
    }
    
    @Override
    public boolean updatePromotion(PromotionEntity promotion) {
        if (promotion == null || promotion.getId() == null || promotion.getId() <= 0) {
            return false;
        }
        
        return promotionDao.updatePromotion(promotion);
    }
    
    @Override
    public boolean deletePromotion(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        
        return promotionDao.deletePromotion(id);
    }
    
    @Override
    public List<PromotionEntity> getAllValidPromotions() {
        return promotionDao.getAllValidPromotions();
    }
    
    @Override
    public BigDecimal applyPromotionToPrice(Long promotionId, BigDecimal originalPrice) {
        if (promotionId == null || promotionId <= 0 || originalPrice == null) {
            return originalPrice;
        }
        
        PromotionEntity promotion = promotionDao.getPromotionById(promotionId);
        if (promotion != null && promotion.isValid()) {
            return promotion.calculateDiscountedPrice(originalPrice);
        }
        
        return originalPrice;
    }
    
    @Override
    public boolean startFlashSale(String name, BigDecimal discount, int durationHours) {
        if (name == null || name.isEmpty() || discount == null || 
            discount.compareTo(BigDecimal.ZERO) <= 0 || durationHours <= 0) {
            return false;
        }
        
        // 创建限时折扣活动
        PromotionEntity flashSale = new PromotionEntity();
        flashSale.setName(name);
        flashSale.setDescription("限时折扣活动");
        flashSale.setType(1); // 限时折扣
        flashSale.setDiscount(discount);
        flashSale.setMinPurchase(BigDecimal.ZERO);
        
        // 设置活动时间
        Date now = new Date();
        flashSale.setStartTime(now);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, durationHours);
        flashSale.setEndTime(calendar.getTime());
        
        flashSale.setIsActive(true);
        
        return promotionDao.createPromotion(flashSale);
    }
    
    @Override
    public boolean startFullReduction(String name, BigDecimal minPurchase, BigDecimal discount, int durationDays) {
        if (name == null || name.isEmpty() || minPurchase == null || 
            minPurchase.compareTo(BigDecimal.ZERO) <= 0 || discount == null ||
            discount.compareTo(BigDecimal.ZERO) <= 0 || durationDays <= 0) {
            return false;
        }
        
        // 创建满减活动
        PromotionEntity fullReduction = new PromotionEntity();
        fullReduction.setName(name);
        fullReduction.setDescription("满减活动");
        fullReduction.setType(2); // 满减活动
        fullReduction.setDiscount(discount);
        fullReduction.setMinPurchase(minPurchase);
        
        // 设置活动时间
        Date now = new Date();
        fullReduction.setStartTime(now);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, durationDays);
        fullReduction.setEndTime(calendar.getTime());
        
        fullReduction.setIsActive(true);
        
        return promotionDao.createPromotion(fullReduction);
    }
}