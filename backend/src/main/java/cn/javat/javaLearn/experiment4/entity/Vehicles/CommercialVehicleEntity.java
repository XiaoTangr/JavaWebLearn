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
public class CommercialVehicleEntity extends VehicleEntity {
    /**
     * 载重量（吨）
     */
    private double loadCapacity;

    /**
     * 车厢容积（立方米）
     */
    private double cargoVolume;

    @Override
    public String toString() {
        return String.format("<%d> 种类：商用车 品牌：%s 型号：%s 载重：%.2f吨 容积：%.2f立方米 售价：%.2f万元 库存：%d辆",
                getVehicleId(),
                getVehicleBrand(),
                getVehicleModel(),
                getLoadCapacity(),
                getCargoVolume(),
                getVehiclePrice(),
                getVehicleStock()
        );
    }
}