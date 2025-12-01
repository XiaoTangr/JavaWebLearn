package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动统计实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionStatisticsEntity {
    private Long id;
    private Long promotionId;         // 关联的促销活动ID
    private Long couponId;            // 关联的优惠券ID（可为空）
    private Integer participationCount; // 参与人数
    private Integer orderCount;       // 订单数量
    private BigDecimal totalDiscount; // 总折扣金额
    private BigDecimal totalSales;    // 活动期间总销售额
    private Date statisticsDate;      // 统计日期
    
    /**
     * 计算平均订单价值
     *
     * @return 平均订单价值
     */
    public BigDecimal getAverageOrderValue() {
        if (orderCount == null || orderCount == 0) {
            return BigDecimal.ZERO;
        }
        return totalSales.divide(new BigDecimal(orderCount), 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 计算ROI (投资回报率)
     *
     * @param marketingCost 营销成本
     * @return ROI
     */
    public BigDecimal calculateROI(BigDecimal marketingCost) {
        if (marketingCost == null || marketingCost.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        // (总销售额 - 总折扣 - 营销成本) / 营销成本
        BigDecimal profit = totalSales.subtract(totalDiscount).subtract(marketingCost);
        return profit.divide(marketingCost, 4, BigDecimal.ROUND_HALF_UP);
    }
}