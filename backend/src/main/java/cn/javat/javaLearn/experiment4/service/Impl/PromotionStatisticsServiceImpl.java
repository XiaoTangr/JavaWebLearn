package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.dao.PromotionStatisticsDao;
import cn.javat.javaLearn.experiment4.dao.Impl.PromotionStatisticsDaoImpl;
import cn.javat.javaLearn.experiment4.entity.PromotionStatisticsEntity;
import cn.javat.javaLearn.experiment4.service.PromotionStatisticsService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 活动统计服务实现类
 */
public class PromotionStatisticsServiceImpl implements PromotionStatisticsService {
    
    private PromotionStatisticsDao statisticsDao;
    
    public PromotionStatisticsServiceImpl() {
        this.statisticsDao = new PromotionStatisticsDaoImpl();
    }
    
    @Override
    public boolean createStatistics(PromotionStatisticsEntity statistics) {
        if (statistics == null) {
            return false;
        }
        
        return statisticsDao.createStatistics(statistics);
    }
    
    @Override
    public PromotionStatisticsEntity getStatisticsById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        
        return statisticsDao.getStatisticsById(id);
    }
    
    @Override
    public List<PromotionStatisticsEntity> getStatisticsByPromotionId(Long promotionId) {
        if (promotionId == null || promotionId <= 0) {
            return null;
        }
        
        return statisticsDao.getStatisticsByPromotionId(promotionId);
    }
    
    @Override
    public boolean updateStatistics(PromotionStatisticsEntity statistics) {
        if (statistics == null || statistics.getId() == null || statistics.getId() <= 0) {
            return false;
        }
        
        return statisticsDao.updateStatistics(statistics);
    }
    
    @Override
    public boolean deleteStatistics(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        
        return statisticsDao.deleteStatistics(id);
    }
    
    @Override
    public boolean collectStatisticsData(Long promotionId) {
        if (promotionId == null || promotionId <= 0) {
            return false;
        }
        
        // 这里应该从订单系统和其他相关系统收集数据
        // 为了简化，我们创建一个示例统计记录
        PromotionStatisticsEntity statistics = new PromotionStatisticsEntity();
        statistics.setPromotionId(promotionId);
        statistics.setParticipationCount(100); // 示例数据
        statistics.setOrderCount(50); // 示例数据
        statistics.setTotalDiscount(new BigDecimal("5000")); // 示例数据
        statistics.setTotalSales(new BigDecimal("100000")); // 示例数据
        statistics.setStatisticsDate(new Date());
        
        return statisticsDao.createStatistics(statistics);
    }
    
    @Override
    public String analyzePromotionEffect(Long promotionId) {
        if (promotionId == null || promotionId <= 0) {
            return "无效的促销活动ID";
        }
        
        List<PromotionStatisticsEntity> statisticsList = statisticsDao.getStatisticsByPromotionId(promotionId);
        if (statisticsList == null || statisticsList.isEmpty()) {
            return "没有找到该促销活动的统计数据";
        }
        
        StringBuilder analysis = new StringBuilder();
        analysis.append("促销活动效果分析报告\n");
        analysis.append("==================\n");
        
        for (PromotionStatisticsEntity stats : statisticsList) {
            analysis.append("统计日期: ").append(stats.getStatisticsDate()).append("\n");
            analysis.append("参与人数: ").append(stats.getParticipationCount()).append("\n");
            analysis.append("订单数量: ").append(stats.getOrderCount()).append("\n");
            analysis.append("总折扣金额: ").append(stats.getTotalDiscount()).append("\n");
            analysis.append("活动期间总销售额: ").append(stats.getTotalSales()).append("\n");
            analysis.append("平均订单价值: ").append(stats.getAverageOrderValue()).append("\n");
            analysis.append("------------------\n");
        }
        
        return analysis.toString();
    }
    
    @Override
    public BigDecimal calculateROI(Long promotionId, BigDecimal marketingCost) {
        if (promotionId == null || promotionId <= 0 || marketingCost == null) {
            return BigDecimal.ZERO;
        }
        
        List<PromotionStatisticsEntity> statisticsList = statisticsDao.getStatisticsByPromotionId(promotionId);
        if (statisticsList == null || statisticsList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        // 计算总销售额和总折扣
        BigDecimal totalSales = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        
        for (PromotionStatisticsEntity stats : statisticsList) {
            totalSales = totalSales.add(stats.getTotalSales());
            totalDiscount = totalDiscount.add(stats.getTotalDiscount());
        }
        
        // (总销售额 - 总折扣 - 营销成本) / 营销成本
        return totalSales.subtract(totalDiscount).subtract(marketingCost).divide(marketingCost, 4, BigDecimal.ROUND_HALF_UP);
    }
}