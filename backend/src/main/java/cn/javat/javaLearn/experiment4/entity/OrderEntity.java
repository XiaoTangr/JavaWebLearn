package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    private long orderId;
    private long userId;
    private long vehicleId;
    private int buyCount;
    private Double totalPrice;
    private Long createTime;


    @Override
    public String toString() {
        return String.format("<%d> 用户：%d 购买车辆ID：%d 购买数量：%d 订单总价：%.2f元 创建时间：%s",
                getOrderId(),
                getUserId(),
                getVehicleId(),
                getBuyCount(),
                getTotalPrice(),
//                将时间戳转为 xxxx年xx月xx日 xx:xx:xx
                new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(getCreateTime())
        );
    }
}
