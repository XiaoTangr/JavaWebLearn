package cn.javat.javaLearn.experiment4;

import java.lang.reflect.Constructor;

public class Main {

    public static void main(String[] args) {
        try {
            // 使用反射加载UserControllerImpl类
            Class<?> userControllerClass = Class.forName("cn.javat.javaLearn.experiment4.controller.Impl.UserControllerImpl");

            // 获取无参构造函数
            Constructor<?> constructor = userControllerClass.getConstructor();

            // 创建UserController实例
            Object userController = constructor.newInstance();

            // 调用startUp方法
            userController.getClass().getMethod("startUp").invoke(userController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}