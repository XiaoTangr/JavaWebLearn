package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.PromotionEntity;
import java.util.List;

/**
 * 促销活动DAO接口
 */
public interface PromotionDao {
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
     * 获取所有促销活动
     * @return 促销活动列表
     */
    List<PromotionEntity> getAllPromotions();
}