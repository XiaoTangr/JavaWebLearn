package cn.javat.javaLearn.experiment4.service;

import cn.javat.javaLearn.experiment4.entity.PromotionStatisticsEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 * 活动统计服务接口
 */
public interface PromotionStatisticsService {
    /**
     * 创建活动统计记录
     * @param statistics 活动统计对象
     * @return 是否创建成功
     */
    boolean createStatistics(PromotionStatisticsEntity statistics);
    
    /**
     * 根据ID获取活动统计记录
     * @param id 统计记录ID
     * @return 活动统计对象
     */
    PromotionStatisticsEntity getStatisticsById(Long id);
    
    /**
     * 根据促销活动ID获取活动统计记录
     * @param promotionId 促销活动ID
     * @return 活动统计对象列表
     */
    List<PromotionStatisticsEntity> getStatisticsByPromotionId(Long promotionId);
    
    /**
     * 更新活动统计记录
     * @param statistics 活动统计对象
     * @return 是否更新成功
     */
    boolean updateStatistics(PromotionStatisticsEntity statistics);
    
    /**
     * 删除活动统计记录
     * @param id 统计记录ID
     * @return 是否删除成功
     */
    boolean deleteStatistics(Long id);
    
    /**
     * 收集活动统计数据
     * @param promotionId 促销活动ID
     * @return 是否收集成功
     */
    boolean collectStatisticsData(Long promotionId);
    
    /**
     * 分析活动效果
     * @param promotionId 促销活动ID
     * @return 效果分析报告
     */
    String analyzePromotionEffect(Long promotionId);
    
    /**
     * 计算投资回报率
     * @param promotionId 促销活动ID
     * @param marketingCost 营销成本
     * @return 投资回报率
     */
    BigDecimal calculateROI(Long promotionId, BigDecimal marketingCost);
}