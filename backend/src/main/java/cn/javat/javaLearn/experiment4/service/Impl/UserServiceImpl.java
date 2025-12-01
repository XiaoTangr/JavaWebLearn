package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.dao.Impl.UserDaoImpl;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.service.UserService;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final UserDaoImpl userDao = new UserDaoImpl();
    // 使用 volatile 关键字确保多线程环境下的可见性
    private static volatile UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        // 双重检查锁定模式
        if (instance == null) {
            synchronized (UserServiceImpl.class) {
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 注册
     *
     * @param user 用户
     * @return 成功：用户 失败 null
     */
    @Override
    public UserEntity register(UserEntity user) {
        // 检查邮箱是否已存在
        UserEntity existingUser = userDao.selectByEmail(user.getUserEmail());
        if (existingUser != null) {
            // 邮箱已存在，注册失败
            return null;
        }

        // 设置默认状态为激活
        user.setActive(true);

        // 插入新用户
        int result = userDao.insert(user);
        if (result > 0) {
            // 注册成功，返回用户信息
            return user;
        } else {
            // 注册失败
            return null;
        }
    }

    /**
     * 登录
     *
     * @param userEmail    用户邮箱
     * @param userPassword 用户密码
     * @return 用户 登录成功  -1 账号密码错误  -2 账号不存在  -3 账号被禁用
     */
    @Override
    public Object login(String userEmail, String userPassword) {
        // 根据邮箱查找用户
        UserEntity user = userDao.selectByEmail(userEmail);
        if (user == null) {
            // 账号不存在
            return -2;
        }

        if (!user.isActive()) {
            // 账号被禁用
            return -3;
        }

        if (!user.getPassWord().equals(userPassword)) {
            // 账号密码错误
            return -1;
        }

        // 登录成功，返回用户对象
        return user;
    }

    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Override
    public UserEntity selectUser(long userId) {
        return userDao.select(userId);
    }

    /**
     * 查询所有用户
     *
     * @return 所有用户
     */
    @Override
    public ArrayList<UserEntity> selectAllUser() {
        return userDao.selectAll();
    }

    /**
     * 修改用户信息
     *
     * @param user 用户
     * @return 修改结果 0 失败 1 成功
     */
    @Override
    public int updateUser(UserEntity user) {
        return userDao.update(user);
    }
}