package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.config.AppConfig;
import cn.javat.javaLearn.experiment4.controller.OrderController;
import cn.javat.javaLearn.experiment4.controller.UserController;
import cn.javat.javaLearn.experiment4.controller.VehicleController;
import cn.javat.javaLearn.experiment4.controller.CustomerServiceController;
import cn.javat.javaLearn.experiment4.controller.DataBackupController;
import cn.javat.javaLearn.experiment4.controller.CartController;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.service.DataBackupService;
import cn.javat.javaLearn.experiment4.service.Impl.DataBackupServiceImpl;
import cn.javat.javaLearn.experiment4.service.Impl.UserServiceImpl;
import cn.javat.javaLearn.experiment4.service.UserService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

    private static final ThreadLocal<UserEntity> currentUser = new ThreadLocal<>();
    private final Scanner scanner = new Scanner(System.in);
    private final UserService userService = UserServiceImpl.getInstance();
    private final String ADMIN_EMAIL = AppConfig.getInstance().getProperty("user.admin_email");

    private final VehicleController vehicleController = new VehicleControllerImpl();
    private final OrderController orderController = new OrderControllerImpl();
    private final CustomerServiceController customerServiceController = new CustomerServiceControllerImpl();
    private final DataBackupController dataBackupController = new DataBackupControllerImpl();
    private final CartController cartController = new CartControllerImpl();

    //    入口
    @Override
    public void startUp() {
        UserEntity user = currentUser.get();
        if (user != null) {
            if (Objects.equals(user.getUserEmail(), ADMIN_EMAIL)) {
                adminPanel();
            } else {
                userPanel();
            }
            return;
        }
        while (currentUser.get() == null) {
            AppUtils.printDoubleLine();
            AppUtils.print("欢迎访问汽车交易系统！");
            AppUtils.printLine();
            AppUtils.print("1. 登录");
            AppUtils.print("2. 注册");
            AppUtils.print("0. 退出");
            AppUtils.printLine();
            AppUtils.print("请选择：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> {
                    DataBackupService backup = DataBackupServiceImpl.getInstance();
                    backup.backupDatabase();
                    System.exit(0);
                }
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    //     —————————————————— 普通用户 ——————————————————————
    //     普通用户面板
    @Override
    public void userPanel() {
        UserEntity user = currentUser.get();
        if (user == null) {
            AppUtils.print("请先登录！");
            login();
        }
        while (currentUser.get() != null) {
            user = currentUser.get();
            AppUtils.printDoubleLine();
            AppUtils.print("欢迎用户：%s", user.getUserName());
            AppUtils.printLine();
            AppUtils.print("1. 修改当前用户信息");
            AppUtils.print("2. 进入车辆交易系统");
            AppUtils.print("3. 进入订单管理系统");
            AppUtils.print("4. 进入客户服务系统");
            AppUtils.print("5. 购物车管理");
            AppUtils.print("0. 退出登录");
            AppUtils.printLine();
            AppUtils.print("请选择：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> updateUser(user);
                case 2 -> {
                    vehicleController.setCurrentUser(user);
                    vehicleController.startUp();
                }
                case 3 -> {
                    orderController.setCurrentUser(user);
                    orderController.startUp();
                }
                case 4 -> {
                    customerServiceController.setCurrentUser(user);
                    customerServiceController.startUp();
                }
                case 5 -> {
                    cartController.setCurrentUser(user);
                    cartController.startUp();
                }
                case 0 -> logout();
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    //     —————————————————— 管 理 员 ——————————————————————
    //   管理员面板
    @Override
    public void adminPanel() {
        if (currentUser.get() == null) {
            AppUtils.print("请先登录！");
            login();
        }
        if (!currentUser.get().getUserEmail().equals(ADMIN_EMAIL)) {
            AppUtils.print("您没有权限访问此功能！");
            return;
        }
        while (currentUser.get() != null) {
            AppUtils.printDoubleLine();
            AppUtils.print("欢迎管理员：%s", currentUser.get().getUserName());
            AppUtils.printLine();
            AppUtils.print("1. 修改当前用户信息");
            AppUtils.print("2. 进入用户管理系统");
            AppUtils.print("3. 进入车辆管理系统");
            AppUtils.print("4. 进入订单管理系统");
            AppUtils.print("5. 进入客户服务系统");
            AppUtils.print("6. 进入数据备份系统");
            AppUtils.print("0. 退出登录");
            AppUtils.printLine();
            AppUtils.print("请选择：");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> updateUser(currentUser.get());
                case 2 -> updateUserWithAdmin();
                case 3 -> {
                    vehicleController.setCurrentUser(currentUser.get());
                    vehicleController.startUp();
                }
                case 4 -> {
                    orderController.setCurrentUser(currentUser.get());
                    orderController.startUp();
                }
                case 5 -> {
                    customerServiceController.setCurrentUser(currentUser.get());
                    customerServiceController.startUp();
                }
                case 6 -> {
                    dataBackupController.setCurrentUser(currentUser.get());
                    dataBackupController.startUp();
                }
                case 0 -> logout();
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    //     —————————————————— 公    共 ——————————————————————
    //     登录
    @Override
    public void login() {
        while (currentUser.get() == null) {
            AppUtils.print("请输入用户邮箱：");
            String userEmail = scanner.next();
            AppUtils.print("请输入用户密码：");
            String userPassword = scanner.next();
            Object result = userService.login(userEmail, userPassword);
            if (result instanceof UserEntity) {
                currentUser.set((UserEntity) result);
                startUp();
            } else {
                int res = (int) result;
                switch (res) {
                    case -1 -> AppUtils.print("账号密码错误！请重新输入。");
                    case -2 -> AppUtils.print("账号不存在！请重新输入。");
                    case -3 -> AppUtils.print("账号被禁用！请联系管理员。");
                }
            }
        }
    }

    //     注册
    @Override
    public void register() {
        while (currentUser.get() == null) {
//            时间戳为用户ID
            long userId = System.currentTimeMillis();
            AppUtils.print("请输入用户邮箱：");
            String userEmail = scanner.next();
            AppUtils.print("请输入用户名：");
            String userName = scanner.next();
            AppUtils.print("请输入用户密码：");
            String userPassword = scanner.next();
            AppUtils.print("请再次输入密码：");
            String userPassword2 = scanner.next();
            while (!userPassword.equals(userPassword2)) {
                AppUtils.print("两次输入的密码不一致！请重新输入。");
                AppUtils.print("请输入用户密码：");
                userPassword = scanner.next();
                AppUtils.print("请再次输入密码：");
                userPassword2 = scanner.next();
            }
            UserEntity newUser = new UserEntity(userId, userEmail, userName, userPassword, true);
            printUser(newUser);
            AppUtils.print("是否确认注册？(y/n)");
            String choice = scanner.next();
            if (choice.equals("y")) {
                AppUtils.print("注册成功，请登录");
                UserEntity registeredUser = userService.register(newUser);
                if (registeredUser != null) {
                    currentUser.set(registeredUser);
                }
                startUp();
            } else if (choice.equals("n")) {
                AppUtils.print("取消注册！");
            }
        }
    }

    //     登出
    @Override
    public void logout() {
        currentUser.remove();
        AppUtils.print("已退出登录！");
        startUp();
    }

    //    修改用户信息
    @Override
    public void updateUser(UserEntity user) {
        UserEntity withoutEdit = user;
        boolean finishEdit = false;
        while (!finishEdit) {
            AppUtils.printDoubleLine();
            AppUtils.print("修改用户信息（退出：q ）");
            AppUtils.printLine();
            AppUtils.print("用户ID： %s", user.getUserId());
            AppUtils.print("1. 修改用户名（当前设置为：%s）", user.getUserName());
            AppUtils.print("2. 修改邮箱（当前设置为：%s）", user.getUserEmail());
            AppUtils.print("3. 修改密码（当前设置为：%s）", user.getPassWord());
            AppUtils.print("4. 修改账号状态（当前设置为：%s）", user.isActive() ? "正常" : "禁用");
            AppUtils.print("0. 完成");
            AppUtils.printLine();
            AppUtils.print("请选择：");
            String nextInput = scanner.next();
            if (nextInput.equals("q")) {
                return;
            }
            int choice = Math.toIntExact(Long.parseLong(nextInput));
            switch (choice) {
                case 1 -> {
                    AppUtils.print("请输入新用户名：");
                    user.setUserName(scanner.next());
                }
                case 2 -> {
                    AppUtils.print("请输入新用户邮箱：");
                    user.setUserEmail(scanner.next());
                }
                case 3 -> {
                    AppUtils.print("请输入新密码：");
                    user.setPassWord(scanner.next());
                    AppUtils.print("请再次输入密码：");
                    String userPassword = scanner.next();
                    while (!userPassword.equals(user.getPassWord())) {
                        AppUtils.print("两次输入的密码不一致！请重新输入。");
                        AppUtils.print("请输入用户密码：");
                        user.setPassWord(scanner.next());
                        AppUtils.print("请再次输入密码：");
                        userPassword = scanner.next();
                    }
                }
                case 4 -> {
                    AppUtils.print("请输入用户邮箱：");
                    user.setUserEmail(scanner.next());
                }
                case 0 -> finishEdit = true;
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
        AppUtils.print("原始用户信息：");
        printUserInline(withoutEdit);
        AppUtils.print("更新用户信息：");
        printUserInline(user);
        AppUtils.print("是否确认修改？(y/n)");
        String choice2 = scanner.next();
        if (choice2.equals("y")) {
            int result = userService.updateUser(user);
            if (result > 0) {
                // 如果修改的是当前登录用户，更新ThreadLocal中的用户信息
                if (user.getUserId() == currentUser.get().getUserId()) {
                    currentUser.set(user);
                }
                AppUtils.print("修改成功！");
            } else {
                AppUtils.print("修改失败！");
            }
        } else if (choice2.equals("n")) {
            AppUtils.print("取消修改！");
        }
    }

    //     修改用户信息（管理员）
    @Override
    public void updateUserWithAdmin() {
        UserEntity user = currentUser.get();
        if (user == null) {
            AppUtils.print("请先登录！");
            login();
            return;
        }
        if (!user.getUserEmail().equals(ADMIN_EMAIL)) {
            AppUtils.print("您没有权限访问此功能！");
            return;
        }
        while (currentUser.get() != null) {
            ArrayList<UserEntity> users = userService.selectAllUser();
            printAllUser(users);
            AppUtils.print("请输入要修改的用户ID(退出：q ）：");
            String nextInput = scanner.next();
            if (nextInput.equals("q")) {
                return;
            }
            UserEntity targetUser = userService.selectUser(Long.parseLong(nextInput));
            if (targetUser == null) {
                AppUtils.print("用户不存在！");
                continue;
            }
            updateUser(targetUser);
        }

    }

    //     打印用户信息
    @Override
    public void printUser(UserEntity user) {
        AppUtils.printDoubleLine();
        AppUtils.print("用户信息如下：");
        AppUtils.printLine();
        AppUtils.print("用户ID：%s", user.getUserId());
        AppUtils.print("用户邮箱：%s", user.getUserEmail());
        AppUtils.print("用户密码：%s", user.getPassWord());
        AppUtils.print("用户名：%s", user.getUserName());
        AppUtils.print("用户状态：%s", user.isActive() ? "正常" : "禁用");
        AppUtils.printDoubleLine();
    }

    //     打印用户信息（行内）
    @Override
    public void printUserInline(UserEntity user) {
        AppUtils.print(user.toString());
    }

    //     批量打印用户信息
    @Override
    public void printAllUser(ArrayList<UserEntity> users) {
        AppUtils.printDoubleLine();
        AppUtils.print("所有用户信息如下：");
        AppUtils.printLine();
        for (UserEntity user : users) {
            printUserInline(user);
        }
        AppUtils.printDoubleLine();
    }
}