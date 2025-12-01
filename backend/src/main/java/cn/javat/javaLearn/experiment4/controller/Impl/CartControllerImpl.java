package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.controller.CartController;
import cn.javat.javaLearn.experiment4.entity.CartItemEntity;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.service.Impl.CartServiceImpl;
import cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl;
import cn.javat.javaLearn.experiment4.service.CartService;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class CartControllerImpl implements CartController {
    
    private final Scanner scanner = new Scanner(System.in);
    private final CartService cartService = CartServiceImpl.getInstance();
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
            userPanel();
        } else {
            AppUtils.print("请先登录！");
        }
    }

    @Override
    public void userPanel() {
        if (currentUser == null) {
            AppUtils.print("请先登录！");
            return;
        }
        
        while (currentUser != null) {
            AppUtils.printDoubleLine();
            AppUtils.print("购物车管理系统");
            AppUtils.printLine();
            AppUtils.print("1. 查看购物车");
            AppUtils.print("2. 添加商品");
            AppUtils.print("3. 修改数量");
            AppUtils.print("4. 删除商品");
            AppUtils.print("5. 清空购物车");
            AppUtils.print("6. 结算"); // 可以扩展为支持单个商品结算
            AppUtils.print("7. 全部购买"); // 添加全部购买选项
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            
            switch (scanner.nextInt()) {
                case 1 -> viewCart();
                case 2 -> addProduct();
                case 3 -> updateQuantity();
                case 4 -> removeProduct();
                case 5 -> clearCart();
                case 6 -> checkoutSingle(); // 单个商品结算
                case 7 -> checkoutAll(); // 全部购买
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    // 查看购物车
    private void viewCart() {
        ArrayList<CartItemEntity> items = cartService.getCartItems(currentUser.getUserId());
        if (items.isEmpty()) {
            AppUtils.print("购物车为空！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("购物车商品列表");
        AppUtils.printLine();
        AppUtils.print("%-5s %-10s %-15s %-10s %-8s %-10s", "序号", "车辆ID", "品牌型号", "单价(万元)", "数量", "小计(万元)");
        AppUtils.printLine();
        
        double total = 0.0;
        for (int i = 0; i < items.size(); i++) {
            CartItemEntity item = items.get(i);
            VehicleEntity vehicle = vehicleService.selectVehicle(item.getVehicleId());
            
            if (vehicle != null) {
                double subtotal = vehicle.getVehiclePrice() * item.getQuantity();
                total += subtotal;
                
                AppUtils.print("%-5d %-10d %-15s %-12.2f %-8d %-12.2f",
                    i + 1,
                    item.getVehicleId(),
                    vehicle.getVehicleBrand() + " " + vehicle.getVehicleModel(),
                    vehicle.getVehiclePrice(),
                    item.getQuantity(),
                    subtotal
                );
            } else {
                // 车辆不存在的情况
                AppUtils.print("%-5d %-10d %-15s %-12.2f %-8d %-12.2f",
                    i + 1,
                    item.getVehicleId(),
                    "车辆已下架",
                    0.0,
                    item.getQuantity(),
                    0.0
                );
            }
        }
        
        AppUtils.printLine();
        AppUtils.print("总计：%.2f 万元", total);
    }
    
    // 添加商品
    private void addProduct() {
        AppUtils.print("请输入车辆ID：");
        long vehicleId = scanner.nextLong();
        
        // 检查车辆是否存在
        VehicleEntity vehicle = vehicleService.selectVehicle(vehicleId);
        if (vehicle == null) {
            AppUtils.print("车辆不存在！");
            return;
        }
        
        AppUtils.print("请输入购买数量：");
        int quantity = scanner.nextInt();
        
        if (quantity <= 0) {
            AppUtils.print("数量必须大于0！");
            return;
        }
        
        // 检查库存
        if (quantity > vehicle.getVehicleStock()) {
            AppUtils.print("库存不足！当前库存：%d", vehicle.getVehicleStock());
            return;
        }
        
        int result = cartService.addToCart(currentUser.getUserId(), vehicleId, quantity);
        if (result == 0) {
            AppUtils.print("商品已添加到购物车！");
        } else {
            AppUtils.print("添加商品失败！");
        }
    }
    
    // 修改数量
    private void updateQuantity() {
        ArrayList<CartItemEntity> items = cartService.getCartItems(currentUser.getUserId());
        if (items.isEmpty()) {
            AppUtils.print("购物车为空！");
            return;
        }
        
        viewCart();
        AppUtils.print("请输入要修改的商品序号：");
        int index = scanner.nextInt() - 1;
        
        if (index < 0 || index >= items.size()) {
            AppUtils.print("序号无效！");
            return;
        }
        
        CartItemEntity item = items.get(index);
        VehicleEntity vehicle = vehicleService.selectVehicle(item.getVehicleId());
        if (vehicle == null) {
            AppUtils.print("车辆不存在！");
            return;
        }
        
        AppUtils.print("请输入新的数量（当前：%d，库存：%d）：", item.getQuantity(), vehicle.getVehicleStock());
        int quantity = scanner.nextInt();
        
        if (quantity <= 0) {
            AppUtils.print("数量必须大于0！");
            return;
        }
        
        // 检查库存
        if (quantity > vehicle.getVehicleStock()) {
            AppUtils.print("库存不足！当前库存：%d", vehicle.getVehicleStock());
            return;
        }
        
        int result = cartService.updateQuantity(currentUser.getUserId(), item.getVehicleId(), quantity);
        if (result == 0) {
            AppUtils.print("数量已更新！");
        } else {
            AppUtils.print("更新数量失败！");
        }
    }
    
    // 删除商品
    private void removeProduct() {
        ArrayList<CartItemEntity> items = cartService.getCartItems(currentUser.getUserId());
        if (items.isEmpty()) {
            AppUtils.print("购物车为空！");
            return;
        }
        
        viewCart();
        AppUtils.print("请输入要删除的商品序号：");
        int index = scanner.nextInt() - 1;
        
        if (index < 0 || index >= items.size()) {
            AppUtils.print("序号无效！");
            return;
        }
        
        CartItemEntity item = items.get(index);
        int result = cartService.removeFromCart(currentUser.getUserId(), item.getVehicleId());
        if (result == 0) {
            AppUtils.print("商品已从购物车中删除！");
        } else {
            AppUtils.print("删除商品失败！");
        }
    }
    
    // 清空购物车
    private void clearCart() {
        ArrayList<CartItemEntity> items = cartService.getCartItems(currentUser.getUserId());
        if (items.isEmpty()) {
            AppUtils.print("购物车为空！");
            return;
        }
        
        AppUtils.print("确认清空购物车？(y/n)");
        String confirm = scanner.next();
        if (confirm.equalsIgnoreCase("y")) {
            int result = cartService.clearCart(currentUser.getUserId());
            if (result == 0) {
                AppUtils.print("购物车已清空！");
            } else {
                AppUtils.print("清空购物车失败！");
            }
        }
    }
    
    // 结算单个商品
    private void checkoutSingle() {
        ArrayList<CartItemEntity> items = cartService.getCartItems(currentUser.getUserId());
        if (items.isEmpty()) {
            AppUtils.print("购物车为空，无法结算！");
            return;
        }
        
        viewCart();
        AppUtils.print("请输入要结算的商品序号：");
        int index = scanner.nextInt() - 1;
        
        if (index < 0 || index >= items.size()) {
            AppUtils.print("序号无效！");
            return;
        }
        
        CartItemEntity item = items.get(index);
        VehicleEntity vehicle = vehicleService.selectVehicle(item.getVehicleId());
        if (vehicle == null) {
            AppUtils.print("车辆不存在！");
            return;
        }
        
        // 检查库存
        if (item.getQuantity() > vehicle.getVehicleStock()) {
            AppUtils.print("库存不足！当前库存：%d", vehicle.getVehicleStock());
            return;
        }
        
        // 创建订单
        double totalPrice = vehicle.getVehiclePrice() * item.getQuantity();
        long timeStamp = System.currentTimeMillis();
        cn.javat.javaLearn.experiment4.entity.OrderEntity order = new cn.javat.javaLearn.experiment4.entity.OrderEntity(
                timeStamp,
                currentUser.getUserId(),
                vehicle.getVehicleId(),
                item.getQuantity(),
                totalPrice,
                timeStamp
        );
        
        // 显示订单详情
        AppUtils.printDoubleLine();
        AppUtils.print("订单信息确认");
        AppUtils.printLine();
        printOrderDetails(order, vehicle);
        AppUtils.printLine();
        AppUtils.print("是否确认购买？(y/n)");
        String confirm = scanner.next();
        if (confirm.equals("y")) {
            // 调用购买服务
            cn.javat.javaLearn.experiment4.service.VehicleService vehicleService = cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl.getInstance();
            int result = vehicleService.buyVehicle(order);
            switch (result) {
                case 0 -> {
                    AppUtils.print("购买成功！");
                    // 从购物车中移除已购买的商品
                    cartService.removeFromCart(currentUser.getUserId(), item.getVehicleId());
                }
                case -1 -> AppUtils.print("车辆不存在！");
                case -2 -> AppUtils.print("用户不存在！");
                case -3 -> AppUtils.print("库存异常！");
                default -> AppUtils.print("购买失败！");
            }
        } else {
            AppUtils.print("取消购买！");
        }
    }
    
    // 全部购买
    private void checkoutAll() {
        ArrayList<CartItemEntity> items = cartService.getCartItems(currentUser.getUserId());
        if (items.isEmpty()) {
            AppUtils.print("购物车为空，无法结算！");
            return;
        }
        
        viewCart();
        AppUtils.print("确认购买购物车中所有商品？(y/n)");
        String confirm = scanner.next();
        if (confirm.equalsIgnoreCase("y")) {
            boolean hasSuccess = false;
            boolean hasFailure = false;
            
            // 遍历购物车中的所有商品并逐一购买
            for (CartItemEntity item : items) {
                VehicleEntity vehicle = vehicleService.selectVehicle(item.getVehicleId());
                if (vehicle == null) {
                    AppUtils.print("车辆ID %d 不存在，跳过购买！", item.getVehicleId());
                    hasFailure = true;
                    continue;
                }
                
                // 检查库存
                if (item.getQuantity() > vehicle.getVehicleStock()) {
                    AppUtils.print("车辆 %s %s 库存不足！当前库存：%d，需求数量：%d", 
                        vehicle.getVehicleBrand(), vehicle.getVehicleModel(), 
                        vehicle.getVehicleStock(), item.getQuantity());
                    hasFailure = true;
                    continue;
                }
                
                // 创建订单
                double totalPrice = vehicle.getVehiclePrice() * item.getQuantity();
                long timeStamp = System.currentTimeMillis();
                cn.javat.javaLearn.experiment4.entity.OrderEntity order = new cn.javat.javaLearn.experiment4.entity.OrderEntity(
                        timeStamp,
                        currentUser.getUserId(),
                        vehicle.getVehicleId(),
                        item.getQuantity(),
                        totalPrice,
                        timeStamp
                );
                
                // 调用购买服务
                cn.javat.javaLearn.experiment4.service.VehicleService vehicleService = cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl.getInstance();
                int result = vehicleService.buyVehicle(order);
                if (result == 0) {
                    AppUtils.print("车辆 %s %s 购买成功！", vehicle.getVehicleBrand(), vehicle.getVehicleModel());
                    hasSuccess = true;
                } else {
                    AppUtils.print("车辆 %s %s 购买失败！", vehicle.getVehicleBrand(), vehicle.getVehicleModel());
                    hasFailure = true;
                }
            }
            
            // 清空购物车中已成功购买的商品
            if (hasSuccess) {
                cartService.clearCart(currentUser.getUserId());
                if (hasFailure) {
                    AppUtils.print("部分商品购买成功，购物车已清空。仍有部分商品购买失败，请检查！");
                } else {
                    AppUtils.print("所有商品购买成功，购物车已清空！");
                }
            } else {
                AppUtils.print("所有商品购买失败！");
            }
        }
    }
    
    /**
     * 打印订单详细信息
     */
    private void printOrderDetails(cn.javat.javaLearn.experiment4.entity.OrderEntity order, VehicleEntity vehicle) {
        AppUtils.print("# %d @ %s - %s", vehicle.getVehicleId(), vehicle.getVehicleBrand(), vehicle.getVehicleModel());
        if (vehicle instanceof cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity commercialVehicle) {
            AppUtils.print("# 商用 @ %.2f吨 - %.2f立方", commercialVehicle.getLoadCapacity(), commercialVehicle.getCargoVolume());
        }
        if (vehicle instanceof cn.javat.javaLearn.experiment4.entity.Vehicles.PassengerVehicleEntity passengerVehicle) {
            AppUtils.print("# 乘用 @ %d座 - %s", passengerVehicle.getSeatCount(), passengerVehicle.getFuelType());
        }
        AppUtils.print("# 购买数量：%d辆", order.getBuyCount());
        AppUtils.print("# 单价：%.2f万元/辆", vehicle.getVehiclePrice());
        AppUtils.print("# 总价：%.2f万元", order.getTotalPrice());
        AppUtils.printLine();
        AppUtils.print("# 订单ID：%d", order.getOrderId());
        AppUtils.print("# 购买人：%s", currentUser.getUserName());
        AppUtils.print("# 创建于：%s", new java.text.SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(order.getCreateTime()));
    }

}