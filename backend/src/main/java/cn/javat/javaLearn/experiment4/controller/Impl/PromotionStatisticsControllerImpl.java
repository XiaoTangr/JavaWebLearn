package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.controller.PromotionStatisticsController;
import cn.javat.javaLearn.experiment4.entity.PromotionStatisticsEntity;
import cn.javat.javaLearn.experiment4.service.PromotionStatisticsService;
import cn.javat.javaLearn.experiment4.service.Impl.PromotionStatisticsServiceImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * 活动统计控制器实现类
 */
public class PromotionStatisticsControllerImpl implements PromotionStatisticsController {
    
    private PromotionStatisticsService statisticsService;
    
    public PromotionStatisticsControllerImpl() {
        this.statisticsService = new PromotionStatisticsServiceImpl();
    }
    
    @Override
    public boolean createStatistics(PromotionStatisticsEntity statistics) {
        try {
            return statisticsService.createStatistics(statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public PromotionStatisticsEntity getStatisticsById(Long id) {
        try {
            return statisticsService.getStatisticsById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<PromotionStatisticsEntity> getStatisticsByPromotionId(Long promotionId) {
        try {
            return statisticsService.getStatisticsByPromotionId(promotionId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean updateStatistics(PromotionStatisticsEntity statistics) {
        try {
            return statisticsService.updateStatistics(statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteStatistics(Long id) {
        try {
            return statisticsService.deleteStatistics(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean collectStatisticsData(Long promotionId) {
        try {
            return statisticsService.collectStatisticsData(promotionId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String analyzePromotionEffect(Long promotionId) {
        try {
            return statisticsService.analyzePromotionEffect(promotionId);
        } catch (Exception e) {
            e.printStackTrace();
            return "分析失败: " + e.getMessage();
        }
    }
    
    @Override
    public BigDecimal calculateROI(Long promotionId, BigDecimal marketingCost) {
        try {
            return statisticsService.calculateROI(promotionId, marketingCost);
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}