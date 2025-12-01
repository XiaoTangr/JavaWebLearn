package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.PassengerVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;

import java.util.ArrayList;

public interface VehicleDao {
    int insert(VehicleEntity vehicle);

    int update(VehicleEntity vehicle);

    int delete(long pk);

    VehicleEntity select(long pk);

    ArrayList<VehicleEntity> selectAll();

    ArrayList<PassengerVehicleEntity> selectAllPassengerVehicles();

    ArrayList<CommercialVehicleEntity> selectAllCommercialVehicles();
    
    // 搜索功能
    ArrayList<VehicleEntity> searchByKeyword(String keyword);
    
    ArrayList<VehicleEntity> searchByCondition(String brand, Double minPrice, Double maxPrice, String type);
    
    // 库存预警功能
    ArrayList<VehicleEntity> getLowStockVehicles(int threshold);
    
    // 库存统计功能
    double calculateInventoryTurnoverRate();
}