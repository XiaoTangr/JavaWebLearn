package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.CustomerServiceEntity;
import cn.javat.javaLearn.experiment4.entity.ServiceRatingEntity;

import java.util.ArrayList;

public interface CustomerServiceDao {
    // 客户服务相关操作
    int insert(CustomerServiceEntity service);
    int update(CustomerServiceEntity service);
    int delete(long id);
    CustomerServiceEntity select(long id);
    ArrayList<CustomerServiceEntity> selectAll();
    ArrayList<CustomerServiceEntity> selectByUserId(long userId);
    ArrayList<CustomerServiceEntity> selectByType(String type);
    ArrayList<CustomerServiceEntity> selectByStatus(String status);
    
    // 服务评价相关操作
    int insertRating(ServiceRatingEntity rating);
    ArrayList<ServiceRatingEntity> selectRatingsByServiceId(long serviceId);
    double calculateAverageRating(long serviceId);
}