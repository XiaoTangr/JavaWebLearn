package cn.javat.javaLearn.experiment4.entity.Vehicles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class PassengerVehicleEntity extends VehicleEntity {
    /**
     * 座位数
     */
    private int seatCount;

    /**
     * 燃油类型（汽油、柴油、电动、混合动力等）
     */
    private String fuelType;

    @Override
    public String toString() {
        return String.format("<%d> 种类：乘用车 品牌：%s 型号：%s 座位数：%d 燃料类型：%s 售价：%.2f万元 库存：%d辆",
                getVehicleId(),
                getVehicleBrand(),
                getVehicleModel(),
                getSeatCount(),
                getFuelType(),
                getVehiclePrice(),
                getVehicleStock()
        );
    }
}