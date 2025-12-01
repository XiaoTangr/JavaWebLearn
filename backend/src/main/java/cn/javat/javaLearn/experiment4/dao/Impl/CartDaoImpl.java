package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.CartDao;
import cn.javat.javaLearn.experiment4.entity.CartItemEntity;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDaoImpl implements CartDao {

    @Override
    public int insert(CartItemEntity cartItem) {
        String sql = "INSERT INTO shopping_cart (user_id, vehicle_id, quantity, create_time, update_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, cartItem.getUserId());
            preparedStatement.setLong(2, cartItem.getVehicleId());
            preparedStatement.setInt(3, cartItem.getQuantity());
            preparedStatement.setLong(4, cartItem.getCreateTime());
            preparedStatement.setLong(5, cartItem.getUpdateTime());
            
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cartItem.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            AppUtils.print("添加商品到购物车失败: " + e);
            return -1;
        }
    }

    @Override
    public int update(CartItemEntity cartItem) {
        String sql = "UPDATE shopping_cart SET quantity=?, update_time=? WHERE id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cartItem.getQuantity());
            preparedStatement.setLong(2, cartItem.getUpdateTime());
            preparedStatement.setLong(3, cartItem.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("更新购物车项目失败: " + e);
            return -1;
        }
    }

    @Override
    public int delete(long id) {
        String sql = "DELETE FROM shopping_cart WHERE id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("删除购物车项目失败: " + e);
            return -1;
        }
    }

    @Override
    public CartItemEntity select(long id) {
        String sql = "SELECT * FROM shopping_cart WHERE id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return CartItemEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .vehicleId(resultSet.getLong("vehicle_id"))
                        .quantity(resultSet.getInt("quantity"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
            }
        } catch (SQLException e) {
            AppUtils.print("查询购物车项目失败: " + e);
        }
        return null;
    }

    @Override
    public CartItemEntity selectByUserAndVehicle(long userId, long vehicleId) {
        String sql = "SELECT * FROM shopping_cart WHERE user_id=? AND vehicle_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, vehicleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return CartItemEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .vehicleId(resultSet.getLong("vehicle_id"))
                        .quantity(resultSet.getInt("quantity"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
            }
        } catch (SQLException e) {
            AppUtils.print("查询用户购物车项目失败: " + e);
        }
        return null;
    }

    @Override
    public ArrayList<CartItemEntity> selectByUserId(long userId) {
        ArrayList<CartItemEntity> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM shopping_cart WHERE user_id=? ORDER BY create_time DESC";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CartItemEntity cartItem = CartItemEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .vehicleId(resultSet.getLong("vehicle_id"))
                        .quantity(resultSet.getInt("quantity"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            AppUtils.print("查询用户购物车失败: " + e);
        }
        return cartItems;
    }

    @Override
    public int clearCart(long userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("清空购物车失败: " + e);
            return -1;
        }
    }
}