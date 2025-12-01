import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class VehicleSearchTest {

    @Test
    public void testSearchByKeyword() {
        VehicleService vehicleService = VehicleServiceImpl.getInstance();
        
        // 测试关键词搜索
        ArrayList<VehicleEntity> result = vehicleService.searchByKeyword("宝马");
        AppUtils.print("关键词搜索结果:");
        for (VehicleEntity vehicle : result) {
            AppUtils.print(vehicle.toString());
        }
    }

    @Test
    public void testSearchByCondition() {
        VehicleService vehicleService = VehicleServiceImpl.getInstance();
        
        // 测试条件筛选
        ArrayList<VehicleEntity> result = vehicleService.searchByCondition("宝马", 10.0, 50.0, "passenger");
        AppUtils.print("条件筛选结果:");
        for (VehicleEntity vehicle : result) {
            AppUtils.print(vehicle.toString());
        }
    }
}