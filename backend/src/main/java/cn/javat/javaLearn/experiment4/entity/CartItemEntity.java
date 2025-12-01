package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemEntity {
    private long id;
    private long userId;
    private long vehicleId;
    private int quantity;
    private long createTime;
    private long updateTime;

    @Override
    public String toString() {
        return String.format("<%d> 用户ID：%d 车辆ID：%d 数量：%d",
                getId(),
                getUserId(),
                getVehicleId(),
                getQuantity()
        );
    }
}