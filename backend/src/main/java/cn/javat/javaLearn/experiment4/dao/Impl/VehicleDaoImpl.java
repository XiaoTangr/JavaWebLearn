package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.VehicleDao;
import cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.PassengerVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDaoImpl implements VehicleDao {
    @Override
    public int insert(VehicleEntity vehicle) {
        String sql = "INSERT INTO vehicles (vehicle_type, vehicle_brand, vehicle_model, vehicle_price, vehicle_stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, vehicle.getVehicleType());
            preparedStatement.setString(2, vehicle.getVehicleBrand());
            preparedStatement.setString(3, vehicle.getVehicleModel());
            preparedStatement.setDouble(4, vehicle.getVehiclePrice());
            preparedStatement.setInt(5, vehicle.getVehicleStock());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vehicle.setVehicleId(generatedKeys.getLong(1));
                    }
                }
            }
            // 根据不同类型插入到对应的详细表
            if (vehicle instanceof PassengerVehicleEntity) {
                result = insertPassengerVehicleDetails((PassengerVehicleEntity) vehicle);
            } else if (vehicle instanceof CommercialVehicleEntity) {
                result = insertCommercialVehicleDetails((CommercialVehicleEntity) vehicle);
            }

            return result;
        } catch (SQLException e) {
            AppUtils.print("插入车辆信息失败: " + e);
            return -1;
        }
    }

    private int insertPassengerVehicleDetails(PassengerVehicleEntity vehicle) {
        String sql = "INSERT INTO passenger_vehicles (vehicle_id, seat_count, fuel_type) VALUES (?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, vehicle.getVehicleId());
            preparedStatement.setInt(2, vehicle.getSeatCount());
            preparedStatement.setString(3, vehicle.getFuelType());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("插入乘用车信息失败: " + e);
            return -1;
        }
    }

    private int insertCommercialVehicleDetails(CommercialVehicleEntity vehicle) {
        String sql = "INSERT INTO commercial_vehicles (vehicle_id, load_capacity, cargo_volume) VALUES (?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, vehicle.getVehicleId());
            preparedStatement.setDouble(2, vehicle.getLoadCapacity());
            preparedStatement.setDouble(3, vehicle.getCargoVolume());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("插入商用车信息失败: " + e);
            return -1;
        }
    }

    @Override
    public int update(VehicleEntity vehicle) {
        String sql = "UPDATE vehicles SET vehicle_type=?, vehicle_brand=?, vehicle_model=?, vehicle_price=?, vehicle_stock=? WHERE vehicle_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vehicle.getVehicleType());
            preparedStatement.setString(2, vehicle.getVehicleBrand());
            preparedStatement.setString(3, vehicle.getVehicleModel());
            preparedStatement.setDouble(4, vehicle.getVehiclePrice());
            preparedStatement.setInt(5, vehicle.getVehicleStock());
            preparedStatement.setLong(6, vehicle.getVehicleId());

            int result = preparedStatement.executeUpdate();

            // 根据不同类型更新对应的详细表
            if (vehicle instanceof PassengerVehicleEntity) {
                updatePassengerVehicleDetails((PassengerVehicleEntity) vehicle);
            } else if (vehicle instanceof CommercialVehicleEntity) {
                updateCommercialVehicleDetails((CommercialVehicleEntity) vehicle);
            }

            return result;
        } catch (SQLException e) {
            AppUtils.print("更新车辆信息失败: " + e);
            return -1;
        }
    }

    private void updatePassengerVehicleDetails(PassengerVehicleEntity vehicle) {
        String sql = "UPDATE passenger_vehicles SET seat_count=?, fuel_type=? WHERE vehicle_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, vehicle.getSeatCount());
            preparedStatement.setString(2, vehicle.getFuelType());
            preparedStatement.setLong(3, vehicle.getVehicleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("更新乘用车信息失败: " + e);
        }
    }

    private void updateCommercialVehicleDetails(CommercialVehicleEntity vehicle) {
        String sql = "UPDATE commercial_vehicles SET load_capacity=?, cargo_volume=? WHERE vehicle_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, vehicle.getLoadCapacity());
            preparedStatement.setDouble(2, vehicle.getCargoVolume());
            preparedStatement.setLong(3, vehicle.getVehicleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("更新商用车信息失败: " + e);
        }
    }

    @Override
    public int delete(long pk) {
        String sql = "DELETE FROM vehicles WHERE vehicle_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, pk);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("删除车辆信息失败: " + e);
            return -1;
        }
    }

    @Override
    public VehicleEntity select(long pk) {
        String sql = "SELECT v.*, pv.seat_count, pv.fuel_type, cv.load_capacity, cv.cargo_volume " +
                "FROM vehicles v " +
                "LEFT JOIN passenger_vehicles pv ON v.vehicle_id = pv.vehicle_id " +
                "LEFT JOIN commercial_vehicles cv ON v.vehicle_id = cv.vehicle_id " +
                "WHERE v.vehicle_id=?";

        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, pk);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createVehicleFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            AppUtils.print("查询车辆信息失败: " + e);
        }
        return null;
    }


    @Override
    public ArrayList<VehicleEntity> selectAll() {
        ArrayList<VehicleEntity> vehicles = new ArrayList<>();
        String sql = "SELECT v.*, pv.seat_count, pv.fuel_type, cv.load_capacity, cv.cargo_volume " +
                "FROM vehicles v " +
                "LEFT JOIN passenger_vehicles pv ON v.vehicle_id = pv.vehicle_id " +
                "LEFT JOIN commercial_vehicles cv ON v.vehicle_id = cv.vehicle_id";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VehicleEntity vehicle = createVehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            AppUtils.print("查询所有车辆信息失败: " + e);
        }
        return vehicles;
    }

    @Override
    public ArrayList<PassengerVehicleEntity> selectAllPassengerVehicles() {
        ArrayList<PassengerVehicleEntity> vehicles = new ArrayList<>();
        String sql = "SELECT v.*, pv.seat_count, pv.fuel_type FROM vehicles v " +
                "JOIN passenger_vehicles pv ON v.vehicle_id = pv.vehicle_id " +
                "WHERE v.vehicle_type='passenger'";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PassengerVehicleEntity vehicle = PassengerVehicleEntity.builder()
                        .vehicleId(resultSet.getLong("vehicle_id"))
                        .vehicleType(resultSet.getString("vehicle_type"))
                        .vehicleBrand(resultSet.getString("vehicle_brand"))
                        .vehicleModel(resultSet.getString("vehicle_model"))
                        .vehiclePrice(resultSet.getDouble("vehicle_price"))
                        .vehicleStock(resultSet.getInt("vehicle_stock"))
                        .seatCount(resultSet.getInt("seat_count"))
                        .fuelType(resultSet.getString("fuel_type"))
                        .build();
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            AppUtils.print("查询所有乘用车信息失败: " + e);
        }
        return vehicles;
    }

    @Override
    public ArrayList<CommercialVehicleEntity> selectAllCommercialVehicles() {
        ArrayList<CommercialVehicleEntity> vehicles = new ArrayList<>();
        String sql = "SELECT v.*, cv.load_capacity, cv.cargo_volume FROM vehicles v " +
                "JOIN commercial_vehicles cv ON v.vehicle_id = cv.vehicle_id " +
                "WHERE v.vehicle_type='commercial'";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommercialVehicleEntity vehicle = CommercialVehicleEntity.builder()
                        .vehicleId(resultSet.getLong("vehicle_id"))
                        .vehicleType(resultSet.getString("vehicle_type"))
                        .vehicleBrand(resultSet.getString("vehicle_brand"))
                        .vehicleModel(resultSet.getString("vehicle_model"))
                        .vehiclePrice(resultSet.getDouble("vehicle_price"))
                        .vehicleStock(resultSet.getInt("vehicle_stock"))
                        .loadCapacity(resultSet.getDouble("load_capacity"))
                        .cargoVolume(resultSet.getDouble("cargo_volume"))
                        .build();
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            AppUtils.print("查询所有商用车信息失败: " + e);
        }
        return vehicles;
    }

    /**
     * 根据结果集创建适当的Vehicle对象
     *
     * @param resultSet 查询结果集
     * @return 相应类型的Vehicle对象
     * @throws SQLException SQL异常
     */
    private VehicleEntity createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        String vehicleType = resultSet.getString("vehicle_type");

        if ("passenger".equals(vehicleType)) {
            return PassengerVehicleEntity.builder()
                    .vehicleId(resultSet.getLong("vehicle_id"))
                    .vehicleType(resultSet.getString("vehicle_type"))
                    .vehicleBrand(resultSet.getString("vehicle_brand"))
                    .vehicleModel(resultSet.getString("vehicle_model"))
                    .vehiclePrice(resultSet.getDouble("vehicle_price"))
                    .vehicleStock(resultSet.getInt("vehicle_stock"))
                    .seatCount(resultSet.getInt("seat_count"))
                    .fuelType(resultSet.getString("fuel_type"))
                    .build();
        } else if ("commercial".equals(vehicleType)) {
            return CommercialVehicleEntity.builder()
                    .vehicleId(resultSet.getLong("vehicle_id"))
                    .vehicleType(resultSet.getString("vehicle_type"))
                    .vehicleBrand(resultSet.getString("vehicle_brand"))
                    .vehicleModel(resultSet.getString("vehicle_model"))
                    .vehiclePrice(resultSet.getDouble("vehicle_price"))
                    .vehicleStock(resultSet.getInt("vehicle_stock"))
                    .loadCapacity(resultSet.getDouble("load_capacity"))
                    .cargoVolume(resultSet.getDouble("cargo_volume"))
                    .build();
        } else {
            return VehicleEntity.builder()
                    .vehicleId(resultSet.getLong("vehicle_id"))
                    .vehicleType(resultSet.getString("vehicle_type"))
                    .vehicleBrand(resultSet.getString("vehicle_brand"))
                    .vehicleModel(resultSet.getString("vehicle_model"))
                    .vehiclePrice(resultSet.getDouble("vehicle_price"))
                    .vehicleStock(resultSet.getInt("vehicle_stock"))
                    .build();
        }
    }
    
    @Override
    public ArrayList<VehicleEntity> searchByKeyword(String keyword) {
        ArrayList<VehicleEntity> vehicles = new ArrayList<>();
        String sql = "SELECT v.*, pv.seat_count, pv.fuel_type, cv.load_capacity, cv.cargo_volume " +
                "FROM vehicles v " +
                "LEFT JOIN passenger_vehicles pv ON v.vehicle_id = pv.vehicle_id " +
                "LEFT JOIN commercial_vehicles cv ON v.vehicle_id = cv.vehicle_id " +
                "WHERE v.vehicle_brand LIKE ? OR v.vehicle_model LIKE ?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VehicleEntity vehicle = createVehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            AppUtils.print("根据关键词搜索车辆信息失败: " + e);
        }
        return vehicles;
    }

    @Override
    public ArrayList<VehicleEntity> searchByCondition(String brand, Double minPrice, Double maxPrice, String type) {
        ArrayList<VehicleEntity> vehicles = new ArrayList<>();
        
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT v.*, pv.seat_count, pv.fuel_type, cv.load_capacity, cv.cargo_volume ")
                  .append("FROM vehicles v ")
                  .append("LEFT JOIN passenger_vehicles pv ON v.vehicle_id = pv.vehicle_id ")
                  .append("LEFT JOIN commercial_vehicles cv ON v.vehicle_id = cv.vehicle_id ")
                  .append("WHERE 1=1 ");
        
        if (brand != null && !brand.isEmpty()) {
            sqlBuilder.append("AND v.vehicle_brand = ? ");
        }
        
        if (minPrice != null) {
            sqlBuilder.append("AND v.vehicle_price >= ? ");
        }
        
        if (maxPrice != null) {
            sqlBuilder.append("AND v.vehicle_price <= ? ");
        }
        
        if (type != null && !type.isEmpty()) {
            sqlBuilder.append("AND v.vehicle_type = ? ");
        }
        
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            
            int paramIndex = 1;
            if (brand != null && !brand.isEmpty()) {
                preparedStatement.setString(paramIndex++, brand);
            }
            
            if (minPrice != null) {
                preparedStatement.setDouble(paramIndex++, minPrice);
            }
            
            if (maxPrice != null) {
                preparedStatement.setDouble(paramIndex++, maxPrice);
            }
            
            if (type != null && !type.isEmpty()) {
                preparedStatement.setString(paramIndex++, type);
            }
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VehicleEntity vehicle = createVehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            AppUtils.print("根据条件搜索车辆信息失败: " + e);
        }
        return vehicles;
    }
    
    @Override
    public ArrayList<VehicleEntity> getLowStockVehicles(int threshold) {
        ArrayList<VehicleEntity> vehicles = new ArrayList<>();
        String sql = "SELECT v.*, pv.seat_count, pv.fuel_type, cv.load_capacity, cv.cargo_volume " +
                "FROM vehicles v " +
                "LEFT JOIN passenger_vehicles pv ON v.vehicle_id = pv.vehicle_id " +
                "LEFT JOIN commercial_vehicles cv ON v.vehicle_id = cv.vehicle_id " +
                "WHERE v.vehicle_stock <= ?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, threshold);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                VehicleEntity vehicle = createVehicleFromResultSet(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            AppUtils.print("查询低库存车辆信息失败: " + e);
        }
        return vehicles;
    }
    
    @Override
    public double calculateInventoryTurnoverRate() {
        String sql = "SELECT SUM(o.buy_count) as total_sold, SUM(v.vehicle_stock + o.buy_count) as average_inventory " +
                "FROM vehicles v " +
                "LEFT JOIN orders o ON v.vehicle_id = o.vehicle_id";
                
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int totalSold = resultSet.getInt("total_sold");
                int averageInventory = resultSet.getInt("average_inventory");
                
                if (averageInventory > 0) {
                    return (double) totalSold / averageInventory;
                }
            }
        } catch (SQLException e) {
            AppUtils.print("计算库存周转率失败: " + e);
        }
        return 0.0;
    }
}