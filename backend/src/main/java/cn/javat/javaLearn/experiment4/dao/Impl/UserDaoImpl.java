package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.UserDao;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    private final String TABLE_NAME = "users";

    @Override
    public UserEntity selectByEmail(String userEmail) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_email=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return UserEntity.builder()
                        .userId(resultSet.getLong("user_id"))
                        .userEmail(resultSet.getString("user_email"))
                        .userName(resultSet.getString("user_name"))
                        .passWord(resultSet.getString("password"))
                        .active(resultSet.getBoolean("active"))
                        .build();
            }
        } catch (SQLException e) {
            AppUtils.print("查询用户信息失败: " + e);
        }

        return null;
    }

    @Override
    public int insert(UserEntity user) {
        String sql = "INSERT INTO " + TABLE_NAME + " (user_email, user_name, password, active) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUserEmail());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassWord());
            preparedStatement.setBoolean(4, user.isActive());

            int result = preparedStatement.executeUpdate();

            // 获取生成的主键
            if (result > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getLong(1));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            AppUtils.print("插入用户信息失败: " + e);
        }
        return -1;
    }

    @Override
    public int update(UserEntity user) {
        String sql = "UPDATE " + TABLE_NAME + " SET user_email=?, user_name=?, password=?, active=? WHERE user_id=?";
        // 确保每次使用时都获取一个新的连接
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserEmail());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassWord());
            preparedStatement.setBoolean(4, user.isActive());
            preparedStatement.setLong(5, user.getUserId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("更新用户信息失败: " + e);
        }
        return -1;
    }

    @Override
    public int delete(long pk) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE user_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, pk);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            AppUtils.print("删除用户信息失败: " + e);
            return -1;
        }
    }

    @Override
    public UserEntity select(long pk) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE user_id=?";
        // 确保每次使用时都获取一个新的连接
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, pk);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return UserEntity.builder()
                        .userId(resultSet.getLong("user_id"))
                        .userEmail(resultSet.getString("user_email"))
                        .userName(resultSet.getString("user_name"))
                        .passWord(resultSet.getString("password"))
                        .active(resultSet.getBoolean("active"))
                        .build();
            }
        } catch (SQLException e) {
            AppUtils.print("根据ID查询用户信息失败: " + e);
        }
        return null;
    }

    @Override
    public ArrayList<UserEntity> selectAll() {
        ArrayList<UserEntity> users = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;

        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserEntity user = UserEntity.builder()
                        .userId(resultSet.getLong("user_id"))
                        .userEmail(resultSet.getString("user_email"))
                        .userName(resultSet.getString("user_name"))
                        .passWord(resultSet.getString("password"))
                        .active(resultSet.getBoolean("active"))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            AppUtils.print("查询所有用户信息失败: " + e);
        }
        return users;
    }
}