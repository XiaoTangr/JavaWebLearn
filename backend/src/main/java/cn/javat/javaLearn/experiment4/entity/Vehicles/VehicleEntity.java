package cn.javat.javaLearn.experiment4.entity.Vehicles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class VehicleEntity {
    //    汽车编号
    private long vehicleId;
    //     汽车类型 用于标识子类类型
    private String vehicleType;
    //    汽车品牌
    private String vehicleBrand;
    //    汽车型号
    private String vehicleModel;
    //    汽车价格
    private double vehiclePrice;
    //    汽车库存
    private int vehicleStock;

    @Override
    public String toString() {
        return String.format("<%d> 种类：默认 品牌：%s 型号：%s 售价：%.2f万元 库存：%d辆", getVehicleId(), getVehicleBrand(), getVehicleModel(), getVehiclePrice(), getVehicleStock());
    }
}
