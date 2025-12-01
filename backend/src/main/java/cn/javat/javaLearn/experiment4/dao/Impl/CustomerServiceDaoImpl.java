package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.CustomerServiceDao;
import cn.javat.javaLearn.experiment4.entity.CustomerServiceEntity;
import cn.javat.javaLearn.experiment4.entity.ServiceRatingEntity;
import cn.javat.javaLearn.experiment4.utils.AppUtils;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerServiceDaoImpl implements CustomerServiceDao {
    
    // 客户服务相关操作
    @Override
    public int insert(CustomerServiceEntity service) {
        String sql = "INSERT INTO customer_service (user_id, type, subject, content, status, priority, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, service.getUserId());
            preparedStatement.setString(2, service.getType());
            preparedStatement.setString(3, service.getSubject());
            preparedStatement.setString(4, service.getContent());
            preparedStatement.setString(5, service.getStatus());
            preparedStatement.setString(6, service.getPriority());
            preparedStatement.setLong(7, service.getCreateTime());
            preparedStatement.setLong(8, service.getUpdateTime() != null ? service.getUpdateTime() : service.getCreateTime());
            
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        service.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            AppUtils.print("插入客户服务信息失败: " + e);
            return -1;
        }
    }

    @Override
    public int update(CustomerServiceEntity service) {
        String sql = "UPDATE customer_service SET user_id=?, type=?, subject=?, content=?, status=?, priority=?, update_time=? WHERE id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, service.getUserId());
            preparedStatement.setString(2, service.getType());
            preparedStatement.setString(3, service.getSubject());
            preparedStatement.setString(4, service.getContent());
            preparedStatement.setString(5, service.getStatus());
            preparedStatement.setString(6, service.getPriority());
            preparedStatement.setLong(7, service.getUpdateTime());
            preparedStatement.setLong(8, service.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("更新客户服务信息失败: " + e);
            return -1;
        }
    }

    @Override
    public int delete(long id) {
        String sql = "DELETE FROM customer_service WHERE id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AppUtils.print("删除客户服务信息失败: " + e);
            return -1;
        }
    }

    @Override
    public CustomerServiceEntity select(long id) {
        String sql = "SELECT * FROM customer_service WHERE id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return CustomerServiceEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .type(resultSet.getString("type"))
                        .subject(resultSet.getString("subject"))
                        .content(resultSet.getString("content"))
                        .status(resultSet.getString("status"))
                        .priority(resultSet.getString("priority"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
            }
        } catch (SQLException e) {
            AppUtils.print("查询客户服务信息失败: " + e);
        }
        return null;
    }

    @Override
    public ArrayList<CustomerServiceEntity> selectAll() {
        ArrayList<CustomerServiceEntity> services = new ArrayList<>();
        String sql = "SELECT * FROM customer_service ORDER BY create_time DESC";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerServiceEntity service = CustomerServiceEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .type(resultSet.getString("type"))
                        .subject(resultSet.getString("subject"))
                        .content(resultSet.getString("content"))
                        .status(resultSet.getString("status"))
                        .priority(resultSet.getString("priority"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
                services.add(service);
            }
        } catch (SQLException e) {
            AppUtils.print("查询所有客户服务信息失败: " + e);
        }
        return services;
    }

    @Override
    public ArrayList<CustomerServiceEntity> selectByUserId(long userId) {
        ArrayList<CustomerServiceEntity> services = new ArrayList<>();
        String sql = "SELECT * FROM customer_service WHERE user_id=? ORDER BY create_time DESC";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerServiceEntity service = CustomerServiceEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .type(resultSet.getString("type"))
                        .subject(resultSet.getString("subject"))
                        .content(resultSet.getString("content"))
                        .status(resultSet.getString("status"))
                        .priority(resultSet.getString("priority"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
                services.add(service);
            }
        } catch (SQLException e) {
            AppUtils.print("根据用户ID查询客户服务信息失败: " + e);
        }
        return services;
    }

    @Override
    public ArrayList<CustomerServiceEntity> selectByType(String type) {
        ArrayList<CustomerServiceEntity> services = new ArrayList<>();
        String sql = "SELECT * FROM customer_service WHERE type=? ORDER BY create_time DESC";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerServiceEntity service = CustomerServiceEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .type(resultSet.getString("type"))
                        .subject(resultSet.getString("subject"))
                        .content(resultSet.getString("content"))
                        .status(resultSet.getString("status"))
                        .priority(resultSet.getString("priority"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
                services.add(service);
            }
        } catch (SQLException e) {
            AppUtils.print("根据类型查询客户服务信息失败: " + e);
        }
        return services;
    }

    @Override
    public ArrayList<CustomerServiceEntity> selectByStatus(String status) {
        ArrayList<CustomerServiceEntity> services = new ArrayList<>();
        String sql = "SELECT * FROM customer_service WHERE status=? ORDER BY create_time DESC";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerServiceEntity service = CustomerServiceEntity.builder()
                        .id(resultSet.getLong("id"))
                        .userId(resultSet.getLong("user_id"))
                        .type(resultSet.getString("type"))
                        .subject(resultSet.getString("subject"))
                        .content(resultSet.getString("content"))
                        .status(resultSet.getString("status"))
                        .priority(resultSet.getString("priority"))
                        .createTime(resultSet.getLong("create_time"))
                        .updateTime(resultSet.getLong("update_time"))
                        .build();
                services.add(service);
            }
        } catch (SQLException e) {
            AppUtils.print("根据状态查询客户服务信息失败: " + e);
        }
        return services;
    }
    
    // 服务评价相关操作
    @Override
    public int insertRating(ServiceRatingEntity rating) {
        String sql = "INSERT INTO service_ratings (service_id, user_id, rating, comment, create_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, rating.getServiceId());
            preparedStatement.setLong(2, rating.getUserId());
            preparedStatement.setInt(3, rating.getRating());
            preparedStatement.setString(4, rating.getComment());
            preparedStatement.setLong(5, rating.getCreateTime());
            
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        rating.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return result;
        } catch (SQLException e) {
            AppUtils.print("插入服务评价信息失败: " + e);
            return -1;
        }
    }

    @Override
    public ArrayList<ServiceRatingEntity> selectRatingsByServiceId(long serviceId) {
        ArrayList<ServiceRatingEntity> ratings = new ArrayList<>();
        String sql = "SELECT * FROM service_ratings WHERE service_id=? ORDER BY create_time DESC";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, serviceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ServiceRatingEntity rating = ServiceRatingEntity.builder()
                        .id(resultSet.getLong("id"))
                        .serviceId(resultSet.getLong("service_id"))
                        .userId(resultSet.getLong("user_id"))
                        .rating(resultSet.getInt("rating"))
                        .comment(resultSet.getString("comment"))
                        .createTime(resultSet.getLong("create_time"))
                        .build();
                ratings.add(rating);
            }
        } catch (SQLException e) {
            AppUtils.print("根据服务ID查询评价信息失败: " + e);
        }
        return ratings;
    }

    @Override
    public double calculateAverageRating(long serviceId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM service_ratings WHERE service_id=?";
        try (Connection connection = DBUtils.getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, serviceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            AppUtils.print("计算平均评分失败: " + e);
        }
        return 0.0;
    }
}