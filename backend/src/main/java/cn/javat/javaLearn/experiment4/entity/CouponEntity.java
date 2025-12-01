package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponEntity {
    private Long id;
    private String code;              // 优惠券代码
    private String name;              // 优惠券名称
    private BigDecimal discount;      // 折扣金额或比例
    private BigDecimal minAmount;     // 最低消费金额
    private Date startTime;           // 开始时间
    private Date endTime;             // 结束时间
    private Boolean isActive;         // 是否激活
    private Integer totalCount;       // 总数量
    private Integer usedCount;        // 已使用数量

    /**
     * 检查优惠券是否有效
     *
     * @return boolean
     */
    public boolean isValid() {
        Date now = new Date();
        return isActive &&
                now.after(startTime) &&
                now.before(endTime) &&
                usedCount < totalCount;
    }

    /**
     * 使用优惠券
     */
    public void use() {
        if (isValid() && usedCount < totalCount) {
            usedCount++;
        }
    }
}