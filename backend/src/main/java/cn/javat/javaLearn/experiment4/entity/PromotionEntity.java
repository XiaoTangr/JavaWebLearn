package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 促销活动实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionEntity {
    // Getter和Setter方法
    private Long id;
    private String name;              // 活动名称
    private String description;       // 活动描述
    private Integer type;             // 活动类型：1-限时折扣，2-满减活动
    private BigDecimal discount;      // 折扣率(如0.9表示9折)或满减金额
    private BigDecimal minPurchase;   // 最低购买金额（用于满减活动）
    private Date startTime;           // 开始时间
    private Date endTime;             // 结束时间
    private Boolean isActive;         // 是否激活

    /**
     * 检查促销活动是否有效
     *
     * @return boolean
     */
    public boolean isValid() {
        Date now = new Date();
        return isActive &&
                now.after(startTime) &&
                now.before(endTime);
    }

    /**
     * 计算折扣后的价格
     *
     * @param originalPrice 原价
     * @return 折扣后价格
     */
    public BigDecimal calculateDiscountedPrice(BigDecimal originalPrice) {
        if (!isValid()) {
            return originalPrice;
        }

        if (type == 1) {  // 限时折扣
            return originalPrice.multiply(discount);
        } else if (type == 2) {  // 满减活动
            if (originalPrice.compareTo(minPurchase) >= 0) {
                return originalPrice.subtract(discount);
            }
        }
        return originalPrice;
    }
}