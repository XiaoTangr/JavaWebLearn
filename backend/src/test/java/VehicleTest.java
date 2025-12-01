import cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.PassengerVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import org.junit.jupiter.api.Test;

public class VehicleTest {


    @Test
    public void test() {
        VehicleEntity vehicle = new VehicleEntity();
        AppUtils.print(String.valueOf(vehicle));
    }

    @Test
    public void VehicleInsertTest() {
        VehicleService vehicleService = new VehicleServiceImpl();
        // 不要手动设置vehicleId，让数据库自动生成
        CommercialVehicleEntity vehicle = CommercialVehicleEntity.builder()
                .vehicleType("commercial")
                .vehicleBrand("东风")
                .vehicleModel("T10")
                .vehiclePrice(45.25)
                .vehicleStock(10)
                .loadCapacity(10)
                .cargoVolume(4.8)
                .build();

        // 不要手动设置vehicleId，让数据库自动生成
        PassengerVehicleEntity vehicle1 = PassengerVehicleEntity.builder()
                .vehicleType("passenger")
                .vehicleBrand("宝马")
                .vehicleModel("X5")
                .vehiclePrice(20.25)
                .vehicleStock(10)
                .seatCount(5)
                .fuelType("轿车")
                .build();

        // 插入数据
        int res1 = vehicleService.insertVehicle(vehicle);
        int res2 = vehicleService.insertVehicle(vehicle1);

        // 获取插入后的车辆ID
        long vehicleId1 = vehicle.getVehicleId();
        long vehicleId2 = vehicle1.getVehicleId();

//        // 插入后尝试删除
//        vehicleService.DeleteVehicle((int)vehicleId1);
//        vehicleService.DeleteVehicle((int)vehicleId2);

        // 测试断言
        assert res1 == 0;
        assert res2 == 0;
    }

}