package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.OrderDao;
import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {

    private final String TABLE_NAME = "orders";

    /**
     * 插入订单
     *
     * @param order 订单
     * @return 插入结果 >0:受影响的数据行数 -1: 插入失败
     */
    @Override
    public int insert(OrderEntity order) {
        String sql = "INSERT INTO " + TABLE_NAME + " (user_id, vehicle_id, buy_count, total_price, create_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (order.getOrderId() == 0) {
                order.setOrderId(System.currentTimeMillis());
            }
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getVehicleId());
            preparedStatement.setInt(3, order.getBuyCount());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setLong(5, order.getCreateTime());
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setOrderId(generatedKeys.getLong(1));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            AppUtils.print("插入订单信息失败: " + e);
            return -1;
        }
    }

    /**
     * 更新订单
     *
     * @param order 订单
     * @return 返回更新结果 >0:受影响的数据行数 -1: 更新失败
     */
    @Override
    public int update(OrderEntity order) {
        String sql = "UPDATE " + TABLE_NAME + " SET user_id=?, vehicle_id=?, buy_count=?, total_price=?, create_time=? WHERE order_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getVehicleId());
            preparedStatement.setInt(3, order.getBuyCount());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setLong(5, order.getCreateTime());
            preparedStatement.setLong(6, order.getOrderId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("更新订单信息失败: " + e);
            return -1;
        }
    }

    /**
     * 删除订单
     *
     * @param pk 主键
     * @return 删除结果 >0:受影响的数据行数 -1: 删除失败
     */
    @Override
    public int delete(long pk) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE order_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, pk);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("删除订单信息失败: " + e);
            return -1;
        }
    }

    @Override
    public OrderEntity select(long pk) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE order_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, pk);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new OrderEntity(
                        resultSet.getLong("order_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("vehicle_id"),
                        resultSet.getInt("buy_count"),
                        resultSet.getDouble("total_price"),
                        resultSet.getLong("create_time")
                );
            }

        } catch (SQLException e) {
            AppUtils.print("查询订单信息失败: " + e);
        }
        return null;
    }

    /**
     * 查询所有订单
     *
     * @return 订单列表
     */
    @Override
    public ArrayList<OrderEntity> selectAll() {
        ArrayList<OrderEntity> orders = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderEntity order = new OrderEntity(
                        resultSet.getLong("order_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("vehicle_id"),
                        resultSet.getInt("buy_count"),
                        resultSet.getDouble("total_price"),
                        resultSet.getLong("create_time")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            AppUtils.print("查询所有订单信息失败: " + e);
        }
        return orders;
    }

    /**
     * 根据用户ID查询订单
     *
     * @param userId 用户ID
     * @return 订单列表
     */
    @Override
    public ArrayList<OrderEntity> selectByUserId(long userId) {
        ArrayList<OrderEntity> orders = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderEntity order = new OrderEntity(
                            resultSet.getLong("order_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getLong("vehicle_id"),
                            resultSet.getInt("buy_count"),
                            resultSet.getDouble("total_price"),
                            resultSet.getLong("create_time")
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            AppUtils.print("根据用户ID查询订单信息失败: " + e);
        }
        return orders;
    }

    @Override
    public ArrayList<OrderEntity> selectByVehicleId(long vehicleId) {
        ArrayList<OrderEntity> orders = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE vehicle_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, vehicleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderEntity order = new OrderEntity(
                            resultSet.getLong("order_id"),
                            resultSet.getLong("user_id"),
                            resultSet.getLong("vehicle_id"),
                            resultSet.getInt("buy_count"),
                            resultSet.getDouble("total_price"),
                            resultSet.getLong("create_time")
                    );
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            AppUtils.print("根据车辆ID查询订单信息失败: " + e);
        }
        return orders;
    }
}