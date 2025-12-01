package cn.javat.javaLearn.experiment4.controller;

import cn.javat.javaLearn.experiment4.entity.PromotionEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 * 促销活动控制器接口
 */
public interface PromotionController {
    /**
     * 创建促销活动
     * @param promotion 促销活动对象
     * @return 是否创建成功
     */
    boolean createPromotion(PromotionEntity promotion);
    
    /**
     * 根据ID获取促销活动
     * @param id 促销活动ID
     * @return 促销活动对象
     */
    PromotionEntity getPromotionById(Long id);
    
    /**
     * 更新促销活动
     * @param promotion 促销活动对象
     * @return 是否更新成功
     */
    boolean updatePromotion(PromotionEntity promotion);
    
    /**
     * 删除促销活动
     * @param id 促销活动ID
     * @return 是否删除成功
     */
    boolean deletePromotion(Long id);
    
    /**
     * 获取所有有效的促销活动
     * @return 促销活动列表
     */
    List<PromotionEntity> getAllValidPromotions();
    
    /**
     * 应用促销活动到商品价格
     * @param promotionId 促销活动ID
     * @param originalPrice 原始价格
     * @return 折扣后价格
     */
    BigDecimal applyPromotionToPrice(Long promotionId, BigDecimal originalPrice);
    
    /**
     * 启动限时折扣活动
     * @param name 活动名称
     * @param discount 折扣率
     * @param durationHours 持续小时数
     * @return 是否启动成功
     */
    boolean startFlashSale(String name, BigDecimal discount, int durationHours);
    
    /**
     * 启动满减活动
     * @param name 活动名称
     * @param minPurchase 最低购买金额
     * @param discount 满减金额
     * @param durationDays 持续天数
     * @return 是否启动成功
     */
    boolean startFullReduction(String name, BigDecimal minPurchase, BigDecimal discount, int durationDays);
}