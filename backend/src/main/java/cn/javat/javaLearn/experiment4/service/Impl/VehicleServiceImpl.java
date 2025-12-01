package cn.javat.javaLearn.experiment4.service.Impl;


import cn.javat.javaLearn.experiment4.dao.Impl.OrderDaoImpl;
import cn.javat.javaLearn.experiment4.dao.Impl.UserDaoImpl;
import cn.javat.javaLearn.experiment4.dao.Impl.VehicleDaoImpl;
import cn.javat.javaLearn.experiment4.dao.OrderDao;
import cn.javat.javaLearn.experiment4.dao.UserDao;
import cn.javat.javaLearn.experiment4.dao.VehicleDao;
import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleDao vehicleDao = new VehicleDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();

    // 使用 volatile 关键字确保多线程环境下的可见性
    private static volatile VehicleServiceImpl instance;

    public VehicleServiceImpl() {
    }

    public static VehicleServiceImpl getInstance() {
        if (instance == null) {
            synchronized (VehicleServiceImpl.class) {
                if (instance == null) {
                    instance = new VehicleServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 查询车辆
     *
     * @param carId 车辆ID
     * @return 车辆
     */
    @Override
    public VehicleEntity selectVehicle(long carId) {
        return vehicleDao.select(carId);
    }

    /**
     * 获取车辆数量
     *
     * @return 数量
     */
    @Override
    public ArrayList<VehicleEntity> selectAllVehicle() {
        return vehicleDao.selectAll();
    }

    /**
     * 插入车辆
     *
     * @param vehicle 车辆
     * @return 插入结果 0: 插入成功 -1: 插入失败
     */
    @Override
    public int insertVehicle(VehicleEntity vehicle) {
        int result = vehicleDao.insert(vehicle);
        return result > 0 ? 0 : -1;
    }

    /**
     * 更新车辆
     *
     * @param vehicle 车辆
     * @return 0 插入成功 -1 插入失败
     */
    @Override
    public int updateVehicle(VehicleEntity vehicle) {
        int result = vehicleDao.update(vehicle);
        return result > 0 ? 0 : -1;
    }

    /**
     * 删除车辆
     *
     * @param carId 车辆ID
     * @return 0: 删除成功 -1: 删除失败
     */
    @Override
    public int DeleteVehicle(long carId) {
        int result = vehicleDao.delete(carId);
        return result > 0 ? 0 : -1;
    }

    /**
     * 购买车辆
     *
     * @param order 订单
     * @return 0: 购买成功 -1: 车辆不存在 -2：用户不存在 -3: 库存不足
     */
    @Override
    public int buyVehicle(OrderEntity order) {
        long carId = order.getVehicleId();
        long userId = order.getUserId();
        int buyCount = order.getBuyCount();
        // 检查车辆是否存在
        VehicleEntity vehicle = vehicleDao.select(carId);
        if (vehicle == null) {
            return -1; // 车辆不存在
        }
        // 检查用户是否存在
        UserEntity user = userDao.select(userId);
        if (user == null) {
            return -2; // 用户不存在
        }
        // 检查库存是否足够
        if (vehicle.getVehicleStock() < buyCount) {
            return -3; // 库存不足
        }

        // 使用事务保证操作的原子性
        Connection connection = null;
        try {
            connection = DBUtils.getInstance().getConnection();
            connection.setAutoCommit(false); // 开启事务

            // 插入订单
            int orderResult = orderDao.insert(order);

            if (orderResult <= 0) {
                connection.rollback(); // 回滚事务
                return -1; // 订单插入失败
            }
            
            // 更新车辆库存
            vehicle.setVehicleStock(vehicle.getVehicleStock() - buyCount);
            int vehicleResult = vehicleDao.update(vehicle);
            if (vehicleResult <= 0) {
                connection.rollback(); // 回滚事务
                return -1;
            }
            
            connection.commit(); // 提交事务
            return 0; // 购买成功
        } catch (SQLException e) {
            AppUtils.print("购买车辆时发生数据库错误: " + e);
            try {
                connection.rollback(); // 回滚事务
            } catch (SQLException rollbackEx) {
                AppUtils.print("事务回滚失败: " + rollbackEx);
            }
            return -1;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // 恢复自动提交
                } catch (SQLException e) {
                    AppUtils.print("恢复自动提交失败: " + e);
                }
            }
        }
    }
    
    @Override
    public ArrayList<VehicleEntity> searchByKeyword(String keyword) {
        return vehicleDao.searchByKeyword(keyword);
    }
    
    @Override
    public ArrayList<VehicleEntity> searchByCondition(String brand, Double minPrice, Double maxPrice, String type) {
        return vehicleDao.searchByCondition(brand, minPrice, maxPrice, type);
    }
    
    @Override
    public ArrayList<VehicleEntity> getLowStockVehicles(int threshold) {
        return vehicleDao.getLowStockVehicles(threshold);
    }
    
    @Override
    public double calculateInventoryTurnoverRate() {
        return vehicleDao.calculateInventoryTurnoverRate();
    }
}