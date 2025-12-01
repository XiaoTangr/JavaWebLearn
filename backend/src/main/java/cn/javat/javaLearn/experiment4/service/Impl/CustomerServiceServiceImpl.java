package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.dao.Impl.CustomerServiceDaoImpl;
import cn.javat.javaLearn.experiment4.entity.CustomerServiceEntity;
import cn.javat.javaLearn.experiment4.entity.ServiceRatingEntity;
import cn.javat.javaLearn.experiment4.service.CustomerServiceService;

import java.util.ArrayList;

public class CustomerServiceServiceImpl implements CustomerServiceService {
    
    private final CustomerServiceDaoImpl customerServiceDao = new CustomerServiceDaoImpl();
    
    // 使用 volatile 关键字确保多线程环境下的可见性
    private static volatile CustomerServiceServiceImpl instance;

    private CustomerServiceServiceImpl() {
    }

    public static CustomerServiceServiceImpl getInstance() {
        // 双重检查锁定模式
        if (instance == null) {
            synchronized (CustomerServiceServiceImpl.class) {
                if (instance == null) {
                    instance = new CustomerServiceServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 创建客户服务请求
     * @param service 客户服务实体
     * @return 创建结果 0: 成功 -1: 失败
     */
    @Override
    public int createService(CustomerServiceEntity service) {
        int result = customerServiceDao.insert(service);
        return result > 0 ? 0 : -1;
    }

    /**
     * 更新客户服务请求
     * @param service 客户服务实体
     * @return 更新结果 0: 成功 -1: 失败
     */
    @Override
    public int updateService(CustomerServiceEntity service) {
        int result = customerServiceDao.update(service);
        return result > 0 ? 0 : -1;
    }

    /**
     * 删除客户服务请求
     * @param id 服务ID
     * @return 删除结果 0: 成功 -1: 失败
     */
    @Override
    public int deleteService(long id) {
        int result = customerServiceDao.delete(id);
        return result > 0 ? 0 : -1;
    }

    /**
     * 根据ID查询客户服务请求
     * @param id 服务ID
     * @return 客户服务实体
     */
    @Override
    public CustomerServiceEntity selectService(long id) {
        return customerServiceDao.select(id);
    }

    /**
     * 查询所有客户服务请求
     * @return 客户服务实体列表
     */
    @Override
    public ArrayList<CustomerServiceEntity> selectAllServices() {
        return customerServiceDao.selectAll();
    }

    /**
     * 根据用户ID查询客户服务请求
     * @param userId 用户ID
     * @return 客户服务实体列表
     */
    @Override
    public ArrayList<CustomerServiceEntity> selectServicesByUserId(long userId) {
        return customerServiceDao.selectByUserId(userId);
    }

    /**
     * 根据类型查询客户服务请求
     * @param type 服务类型
     * @return 客户服务实体列表
     */
    @Override
    public ArrayList<CustomerServiceEntity> selectServicesByType(String type) {
        return customerServiceDao.selectByType(type);
    }

    /**
     * 根据状态查询客户服务请求
     * @param status 服务状态
     * @return 客户服务实体列表
     */
    @Override
    public ArrayList<CustomerServiceEntity> selectServicesByStatus(String status) {
        return customerServiceDao.selectByStatus(status);
    }

    /**
     * 添加服务评价
     * @param rating 服务评价实体
     * @return 添加结果 0: 成功 -1: 失败
     */
    @Override
    public int addRating(ServiceRatingEntity rating) {
        int result = customerServiceDao.insertRating(rating);
        return result > 0 ? 0 : -1;
    }

    /**
     * 根据服务ID查询评价
     * @param serviceId 服务ID
     * @return 服务评价实体列表
     */
    @Override
    public ArrayList<ServiceRatingEntity> selectRatingsByServiceId(long serviceId) {
        return customerServiceDao.selectRatingsByServiceId(serviceId);
    }

    /**
     * 计算服务的平均评分
     * @param serviceId 服务ID
     * @return 平均评分
     */
    @Override
    public double calculateAverageRating(long serviceId) {
        return customerServiceDao.calculateAverageRating(serviceId);
    }
}