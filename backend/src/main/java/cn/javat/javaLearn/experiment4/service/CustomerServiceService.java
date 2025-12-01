package cn.javat.javaLearn.experiment4.service;

import cn.javat.javaLearn.experiment4.entity.CustomerServiceEntity;
import cn.javat.javaLearn.experiment4.entity.ServiceRatingEntity;

import java.util.ArrayList;

public interface CustomerServiceService {
    /**
     * 创建客户服务请求
     * @param service 客户服务实体
     * @return 创建结果 0: 成功 -1: 失败
     */
    int createService(CustomerServiceEntity service);

    /**
     * 更新客户服务请求
     * @param service 客户服务实体
     * @return 更新结果 0: 成功 -1: 失败
     */
    int updateService(CustomerServiceEntity service);

    /**
     * 删除客户服务请求
     * @param id 服务ID
     * @return 删除结果 0: 成功 -1: 失败
     */
    int deleteService(long id);

    /**
     * 根据ID查询客户服务请求
     * @param id 服务ID
     * @return 客户服务实体
     */
    CustomerServiceEntity selectService(long id);

    /**
     * 查询所有客户服务请求
     * @return 客户服务实体列表
     */
    ArrayList<CustomerServiceEntity> selectAllServices();

    /**
     * 根据用户ID查询客户服务请求
     * @param userId 用户ID
     * @return 客户服务实体列表
     */
    ArrayList<CustomerServiceEntity> selectServicesByUserId(long userId);

    /**
     * 根据类型查询客户服务请求
     * @param type 服务类型
     * @return 客户服务实体列表
     */
    ArrayList<CustomerServiceEntity> selectServicesByType(String type);

    /**
     * 根据状态查询客户服务请求
     * @param status 服务状态
     * @return 客户服务实体列表
     */
    ArrayList<CustomerServiceEntity> selectServicesByStatus(String status);

    /**
     * 添加服务评价
     * @param rating 服务评价实体
     * @return 添加结果 0: 成功 -1: 失败
     */
    int addRating(ServiceRatingEntity rating);

    /**
     * 根据服务ID查询评价
     * @param serviceId 服务ID
     * @return 服务评价实体列表
     */
    ArrayList<ServiceRatingEntity> selectRatingsByServiceId(long serviceId);

    /**
     * 计算服务的平均评分
     * @param serviceId 服务ID
     * @return 平均评分
     */
    double calculateAverageRating(long serviceId);
}