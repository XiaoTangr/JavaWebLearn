package cn.javat.javaLearn.experiment4.dao;

import cn.javat.javaLearn.experiment4.entity.UserEntity;

import java.util.ArrayList;

public interface UserDao {
    int insert(UserEntity user);

    int update(UserEntity user);

    int delete(long pk);

    UserEntity selectByEmail(String userEmail);

    UserEntity select(long pk);

    ArrayList<UserEntity> selectAll();
}
