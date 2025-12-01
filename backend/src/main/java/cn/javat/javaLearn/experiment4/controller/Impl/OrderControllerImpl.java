package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.config.AppConfig;
import cn.javat.javaLearn.experiment4.controller.OrderController;
import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.PassengerVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.service.Impl.OrderServiceImpl;
import cn.javat.javaLearn.experiment4.service.Impl.UserServiceImpl;
import cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl;
import cn.javat.javaLearn.experiment4.service.OrderService;
import cn.javat.javaLearn.experiment4.service.UserService;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class OrderControllerImpl implements OrderController {

    private final String ADMIN_EMAIL = AppConfig.getInstance().getProperty("user.admin_email");
    private final Scanner scanner = new Scanner(System.in);


    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();
    private final VehicleService vehicleService = VehicleServiceImpl.getInstance();

    private UserEntity currentUser = null;


    @Override
    public void setCurrentUser(UserEntity user) {
        this.currentUser = user;
    }

    @Override
    public UserEntity getCurrentUser() {
        return currentUser;
    }

    @Override
    public void startUp() {
        if (currentUser != null) {
            if (isAdmin()) {
                adminPanel();
            } else {
                userPanel();
            }
        } else {
            AppUtils.print("请先登录！");
        }
    }

    private boolean isAdmin() {
        return Objects.equals(currentUser.getUserEmail(), ADMIN_EMAIL);
    }


    //    管理员面板
    public void adminPanel() {
//        打印所有用户的订单
        ArrayList<OrderEntity> orders = orderService.selectAll();

        if (orders.isEmpty()) {
            AppUtils.print("暂无数据!");
            return;
        }
        while (currentUser != null) {
            AppUtils.printDoubleLine();
            AppUtils.print("管理员面板");
            AppUtils.print("所有订单：");
            AppUtils.printLine();
            printOrderList(orders);
            AppUtils.printLine();
            AppUtils.print("1. 查询指定用户的订单");
            AppUtils.print("2. 查询指定车辆的订单");
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            switch (scanner.nextInt()) {
                case 1 -> printOrderListByUserIdWithInput();
                case 2 -> printOrderListByVehicleIdWithInput();
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }


    }


    //用户面板
    public void userPanel() {
        ArrayList<OrderEntity> orders = orderService.selectByUserId(currentUser.getUserId());
        while (currentUser != null) {
            AppUtils.printDoubleLine();
            AppUtils.print("当前用户：" + currentUser.getUserName());
            AppUtils.print("当前用户订单：");
            AppUtils.printLine();
            printOrderList(orders);
            AppUtils.printLine();
            AppUtils.print("1. 查询订单详情");
            AppUtils.print("0. 返回上级");
            switch (scanner.nextInt()) {
                case 1 -> printOrderDetailsWithInput();
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }
    }


    private void printOrderListByUserIdWithInput() {
        AppUtils.print("请输入用户ID：");
        long userId = scanner.nextLong();
        ArrayList<OrderEntity> orders = orderService.selectByUserId(userId);
        while (orders != null) {
            AppUtils.printLine();
            printOrderList(orders);
            AppUtils.printLine();
            AppUtils.print("1. 查询详情");
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            switch (scanner.nextInt()) {
                case 1 -> printOrderDetailsWithInput();
                case 0 -> {
                    orders = null;
                    return;
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }
    }

    private void printOrderListByVehicleIdWithInput() {
        AppUtils.print("请输入车辆ID：");
        long vehicleId = scanner.nextLong();
        ArrayList<OrderEntity> orders = orderService.selectByVehicleId(vehicleId);
        while (orders != null) {
            AppUtils.printLine();
            printOrderList(orders);
            AppUtils.printLine();
            AppUtils.print("1. 查询详情");
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            switch (scanner.nextInt()) {
                case 1 -> printOrderDetailsWithInput();
                case 0 -> {
                    orders = null;
                    return;
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }
    }

    //根据输入打印订单详细信息
    private void printOrderDetailsWithInput() {
        AppUtils.print("请输入订单ID：");
        long orderId = scanner.nextLong();
        OrderEntity order = orderService.select(orderId);
        while (order != null) {
            printOrderDetails(order);
            AppUtils.print("0. 返回上级");
            if (scanner.nextInt() == 0) {
                order = null;
                return;
            } else {
                AppUtils.print("请输入正确的选项！");
            }
        }

    }

    private void printOrderDetails(OrderEntity order) {
        UserEntity user = userService.selectUser(order.getUserId());
        VehicleEntity vehicle = vehicleService.selectVehicle(order.getVehicleId());

        if (user == null || vehicle == null) {
            AppUtils.print("用户或车辆不存在");
            return;
        }
        AppUtils.printDoubleLine();
        AppUtils.print("订单详细信息");
        AppUtils.printLine();
        AppUtils.print("# %d @ %s - %s", vehicle.getVehicleId(), vehicle.getVehicleBrand(), vehicle.getVehicleModel());
        if (vehicle instanceof CommercialVehicleEntity commercialVehicle) {
            AppUtils.print("# 商用 @ %.2f吨 - %.2f立方", commercialVehicle.getLoadCapacity(), commercialVehicle.getCargoVolume());
        }
        if (vehicle instanceof PassengerVehicleEntity passengerVehicle) {
            AppUtils.print("# 乘用 @ %d座 - %s", passengerVehicle.getSeatCount(), passengerVehicle.getFuelType());
        }
        AppUtils.print("# 购买数量：%d辆", order.getBuyCount());
        AppUtils.print("# 单价：%.2f元/辆", vehicle.getVehiclePrice());
        AppUtils.print("# 总价：%.2f元", order.getTotalPrice());
        AppUtils.printLine();
        AppUtils.print("# 订单ID：" + order.getOrderId());
        AppUtils.print("# 购买人：%s - %s", user.getUserName(), user.getUserEmail());
        AppUtils.print("# 创建于：%s", new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(order.getCreateTime()));
        AppUtils.printDoubleLine();
    }


    public void printOrderInline(OrderEntity order) {
        AppUtils.print(order.toString());
    }


    public void printOrderList(ArrayList<OrderEntity> orders) {
        for (OrderEntity order : orders) {
            printOrderInline(order);
        }
    }
}