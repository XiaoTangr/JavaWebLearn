package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.config.AppConfig;
import cn.javat.javaLearn.experiment4.controller.VehicleController;
import cn.javat.javaLearn.experiment4.entity.OrderEntity;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.CommercialVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.PassengerVehicleEntity;
import cn.javat.javaLearn.experiment4.entity.Vehicles.VehicleEntity;
import cn.javat.javaLearn.experiment4.service.Impl.VehicleServiceImpl;
import cn.javat.javaLearn.experiment4.service.VehicleService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class VehicleControllerImpl implements VehicleController {

    private UserEntity currentUser = null;
    private final Scanner scanner = new Scanner(System.in);
    private final VehicleService vehicleService = VehicleServiceImpl.getInstance();

    @Override
    public void setCurrentUser(UserEntity user) {
        this.currentUser = user;
    }

    @Override
    public UserEntity getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public void startUp() {
        AppConfig appConfig = new AppConfig();
        String ADMIN_EMAIL = appConfig.getProperty("user.admin_email");

        if (currentUser == null) {
            AppUtils.print("请先登录！");
        }
        if (currentUser.getUserEmail().equals(ADMIN_EMAIL)) {
            adminPanel();
        } else {
            userPanel();
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
            AppUtils.print("欢迎用户：%s", currentUser.getUserName());
            AppUtils.printLine();
            AppUtils.print("1. 购买车辆"); // 保留原有的购买功能作为立即购买
            AppUtils.print("2. 搜索车辆");
            AppUtils.print("3. 加入购物车"); // 添加加入购物车功能
            AppUtils.print("0. 返回上级");
            switch (scanner.nextInt()) {
                case 1 -> buyVehicle(); // 立即购买
                case 2 -> searchVehicle();
                case 3 -> addToCart(); // 加入购物车
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }
    }
    
    @Override
    public void buyVehicle() {
        if (currentUser == null) {
            AppUtils.print("请先登录！");
            return;
        }
        ArrayList<VehicleEntity> vehicles = vehicleService.selectAllVehicle();
        AppUtils.printDoubleLine();
        AppUtils.print("车辆列表：");
        AppUtils.printLine();
        printAllVehicle(vehicles);
        AppUtils.printLine();
        VehicleEntity vehicle;
        AppUtils.print("请输入购买车辆ID（输入0返回上级）：");
        long vehicleId = scanner.nextLong();
        if (vehicleId == 0) {
            return;
        }
        vehicle = vehicleService.selectVehicle(vehicleId);
        while (vehicle == null) {
            AppUtils.print("车辆不存在！");
            AppUtils.print("请输入车辆ID（输入0返回上级）：");
            vehicleId = scanner.nextLong();
            if (vehicleId == 0) {
                return;
            }
            vehicle = vehicleService.selectVehicle(vehicleId);
        }
        if (vehicle.getVehicleStock() <= 0) {
            AppUtils.print("车辆已售完！");
            return;
        }
        
        // 显示车辆详细信息
        AppUtils.printDoubleLine();
        AppUtils.print("车辆详细信息：");
        printVehicle(vehicle);
        AppUtils.printLine();
        
        // 提供操作选项
        AppUtils.print("请选择操作：");
        AppUtils.print("1. 立即购买");
        AppUtils.print("2. 加入购物车");
        AppUtils.print("0. 返回上级");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                // 继续执行购买流程
                performPurchase(vehicle);
            }
            case 2 -> {
                // 加入购物车
                addToCartDirect(vehicle);
                return;
            }
            case 0 -> {
                return;
            }
            default -> {
                AppUtils.print("无效选择！");
                return;
            }
        }
    }
    
    /**
     * 执行购买流程
     */
    private void performPurchase(VehicleEntity vehicle) {
        int buyCount = 0;
        while (buyCount <= 0) {
            AppUtils.print("请输入购买数量（库存：%d）：", vehicle.getVehicleStock());
            buyCount = scanner.nextInt();
            if (buyCount <= 0) {
                AppUtils.print("请输入正确的数量！");
            }
            if (buyCount > vehicle.getVehicleStock()) {
                AppUtils.print("库存不足！");
                buyCount = 0;
            }
        }
        
        // 计算总价
        double totalPrice = vehicle.getVehiclePrice() * buyCount;
        long timeStamp = System.currentTimeMillis();
        OrderEntity order = new OrderEntity(
                timeStamp,
                currentUser.getUserId(),
                vehicle.getVehicleId(),
                buyCount,
                totalPrice,
                timeStamp
        );

        // 打印订单信息
        AppUtils.printDoubleLine();
        AppUtils.print("订单信息确认");
        AppUtils.printLine();
        printOrderDetails(order, vehicle);
        AppUtils.printLine();
        AppUtils.print("是否确认购买？(y/n)");
        String confirm = scanner.next();
        if (confirm.equals("y")) {
            int result = vehicleService.buyVehicle(order);
            switch (result) {
                case 0 -> AppUtils.print("购买成功！");
                case -1 -> AppUtils.print("车辆不存在！");
                case -2 -> AppUtils.print("用户不存在！");
                case -3 -> AppUtils.print("库存异常！");
                default -> AppUtils.print("购买失败！");
            }
        } else {
            AppUtils.print("取消购买！");
        }
    }
    
    /**
     * 打印订单详细信息
     *
     * @param order   订单
     * @param vehicle 车辆
     */
    private void printOrderDetails(OrderEntity order, VehicleEntity vehicle) {
        AppUtils.print("# %d @ %s - %s", vehicle.getVehicleId(), vehicle.getVehicleBrand(), vehicle.getVehicleModel());
        if (vehicle instanceof CommercialVehicleEntity commercialVehicle) {
            AppUtils.print("# 商用 @ %.2f吨 - %.2f立方", commercialVehicle.getLoadCapacity(), commercialVehicle.getCargoVolume());
        }
        if (vehicle instanceof PassengerVehicleEntity passengerVehicle) {
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

    @Override
    public void adminPanel() {
        while (currentUser != null) {
            ArrayList<VehicleEntity> vehicles = vehicleService.selectAllVehicle();
            AppUtils.printDoubleLine();
            if (vehicles == null || vehicles.isEmpty()) {
                AppUtils.print("暂无车辆！");
                AppUtils.printLine();
                AppUtils.print("1. 添加车辆");
                AppUtils.print("2. 搜索车辆");
                AppUtils.print("0. 退出管理");
                AppUtils.print("请选择操作:");
                switch (scanner.nextInt()) {
                    case 1 -> addVehicle();
                    case 2 -> searchVehicle();
                    case 0 -> {
                        currentUser = null;
                        return;
                    }
                    default -> AppUtils.print("请输入正确的选项！");
                }
            } else {
                AppUtils.print("以下为当前存在车辆:");
                AppUtils.printLine();
                printAllVehicle(vehicles);
                AppUtils.printLine();
                AppUtils.print("1. 添加车辆");
                AppUtils.print("2. 删除车辆");
                AppUtils.print("3. 修改车辆");
                AppUtils.print("4. 搜索车辆");
                AppUtils.print("5. 库存预警");
                AppUtils.print("6. 库存统计");
                AppUtils.print("0. 退出管理");
                AppUtils.print("请选择操作:");
                switch (scanner.nextInt()) {
                    case 1 -> addVehicle();
                    case 2 -> deleteVehicle();
                    case 3 -> updateVehicle();
                    case 4 -> searchVehicle();
                    case 5 -> showLowStockVehicles();
                    case 6 -> showInventoryStatistics();
                    case 0 -> {
                        currentUser = null;
                        return;
                    }
                    default -> AppUtils.print("请输入正确的选项！");
                }
            }
        }
    }

    @Override
    public void addVehicle() {
        VehicleEntity newVehicle = null;
        String vehicleType = "";
        while (true) {
            if (newVehicle == null) {
                AppUtils.print("设置车辆类型（乘/商)");
                String type = scanner.next();
                switch (type) {
                    case "乘" -> {
                        vehicleType = "passenger";
                        newVehicle = new PassengerVehicleEntity();
                        newVehicle.setVehicleType(vehicleType);
                    }
                    case "商" -> {
                        vehicleType = "commercial";
                        newVehicle = new CommercialVehicleEntity();
                        newVehicle.setVehicleType(vehicleType);
                    }
                    default -> {
                        AppUtils.print("请输入正确的车辆类型！");
                        continue;
                    }
                }
            }
            AppUtils.print("1. 设置品牌：%s", newVehicle.getVehicleBrand() != null ? newVehicle.getVehicleBrand() : "");
            AppUtils.print("2. 设置型号: %s", newVehicle.getVehicleModel() != null ? newVehicle.getVehicleModel() : "");
            AppUtils.print("3. 设置单价（万元/辆）: %.2f", newVehicle.getVehiclePrice());
            AppUtils.print("4. 设置库存（辆）: %d", newVehicle.getVehicleStock());
//            设置特定值：
            if (vehicleType.equals("commercial")) {
                AppUtils.print("5. 设置载重（吨）: %.2f", ((CommercialVehicleEntity) newVehicle).getLoadCapacity());
                AppUtils.print("6. 设置容积（立方米）: %.2f", ((CommercialVehicleEntity) newVehicle).getCargoVolume());
            } else {
                AppUtils.print("5. 设置载客量（座）: %d", ((PassengerVehicleEntity) newVehicle).getSeatCount());
                AppUtils.print("6. 设置动力类型: %s", ((PassengerVehicleEntity) newVehicle).getFuelType() != null ? ((PassengerVehicleEntity) newVehicle).getFuelType() : "");
            }
            AppUtils.print("7. 预览结果");
            AppUtils.print("8. 取消添加");

            switch (scanner.nextInt()) {
                case 1 -> {
                    AppUtils.print("请输入车辆品牌:");
                    newVehicle.setVehicleBrand(scanner.next());
                }
                case 2 -> {
                    AppUtils.print("请输入车辆型号:");
                    newVehicle.setVehicleModel(scanner.next());
                }
                case 3 -> {
                    AppUtils.print("请输入车辆单价（万元/辆）:");
                    newVehicle.setVehiclePrice(scanner.nextDouble());
                }
                case 4 -> {
                    AppUtils.print("请输入车辆库存（辆）:");
                    newVehicle.setVehicleStock(scanner.nextInt());
                }
                case 5 -> {
                    if (vehicleType.equals("commercial")) {
                        AppUtils.print("请输入车辆载重（吨）:");
                        ((CommercialVehicleEntity) newVehicle).setLoadCapacity(scanner.nextDouble());
                    } else {
                        AppUtils.print("请输入车辆载客量（座）:");
                        ((PassengerVehicleEntity) newVehicle).setSeatCount(scanner.nextInt());
                    }
                }
                case 6 -> {
                    if (vehicleType.equals("commercial")) {
                        AppUtils.print("请输入车辆容积（立方米）:");
                        ((CommercialVehicleEntity) newVehicle).setCargoVolume(scanner.nextDouble());
                    } else {
                        AppUtils.print("请输入车辆动力类型:");
                        ((PassengerVehicleEntity) newVehicle).setFuelType(scanner.next());
                    }
                }
                case 7 -> {
                    printVehicle(newVehicle);
                    AppUtils.print("是否确定添加车辆？(y/n)");
                    if (scanner.next().equals("y")) {
                        // 设置车辆ID为0，确保是新车辆
                        newVehicle.setVehicleId(0);
                        if (vehicleService.insertVehicle(newVehicle) > -1) {
                            AppUtils.print("添加成功！");
                        } else {
                            AppUtils.print("添加失败！");
                        }
                        return;
                    } else {
                        AppUtils.print("取消添加！");
                    }
                }
                case 0 -> {
                    AppUtils.print("未保存的信息将会丢失！确定取消？（y/n）");
                    if (scanner.next().equals("y")) {
                        AppUtils.print("取消添加！");
                        return;
                    }
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }
    }

    /**
     * 删除车辆
     */
    @Override
    public void deleteVehicle() {
        AppUtils.print("请输入要删除的车辆ID:");
        long carId = scanner.nextLong();
        AppUtils.printLine();
        VehicleEntity vehicle = vehicleService.selectVehicle(carId);
        if (vehicle != null) {
            printVehicleInline(vehicle);
            AppUtils.print("确定要删除上述车辆吗？(y/n)");
            if (scanner.next().equals("y")) {
                if (vehicleService.DeleteVehicle(carId) > -1) {
                    AppUtils.print("删除成功！");
                } else {
                    AppUtils.print("删除失败！");
                }
            } else {
                AppUtils.print("取消删除！");
            }
        }
    }

    /**
     * 修改车辆信息
     *
     */
    @Override
    public void updateVehicle() {
        AppUtils.print("请输入修改目标车辆的ID:");
        long carId = scanner.nextLong();
        VehicleEntity vehicle = vehicleService.selectVehicle(carId);
        while (vehicle == null) {
            AppUtils.print("车辆不存在！请重新输入车辆ID:");
            carId = scanner.nextLong();
            vehicle = vehicleService.selectVehicle(carId);
        }
        // 不允许修改车辆类型，品牌，型号
        String vehicleType = vehicle.getVehicleType();
        while (true) {
            AppUtils.printLine();
            AppUtils.print("只读的信息:");
            AppUtils.print("车辆ID: %d", vehicle.getVehicleId());
            AppUtils.print("车辆类型: %s", Objects.equals(vehicle.getVehicleType(), "commercial") ? "商用车" : "乘用车");
            AppUtils.print("车辆品牌: %s", vehicle.getVehicleBrand());
            AppUtils.print("车辆型号: %s", vehicle.getVehicleModel());
            AppUtils.printLine();
            AppUtils.print("可更新的信息：");
            AppUtils.print("3. 设置单价（万元/辆）: %.2f", vehicle.getVehiclePrice());
            AppUtils.print("4. 设置库存（辆）: %d", vehicle.getVehicleStock());

            if (vehicleType.equals("commercial")) {
                AppUtils.print("5. 设置载重（吨）: %.2f", ((CommercialVehicleEntity) vehicle).getLoadCapacity());
                AppUtils.print("6. 设置容积（立方米）: %.2f", ((CommercialVehicleEntity) vehicle).getCargoVolume());
            } else if (vehicleType.equals("passenger")) {
                AppUtils.print("5. 设置载客量（座）: %d", ((PassengerVehicleEntity) vehicle).getSeatCount());
                AppUtils.print("6. 设置动力类型: %s", ((PassengerVehicleEntity) vehicle).getFuelType() != null ? ((PassengerVehicleEntity) vehicle).getFuelType() : "");
            }

            AppUtils.print("7. 保存修改");
            AppUtils.print("0. 取消修改");

            switch (scanner.nextInt()) {
                case 3 -> {
                    AppUtils.print("请输入车辆单价（万元/辆）:");
                    vehicle.setVehiclePrice(scanner.nextDouble());
                }
                case 4 -> {
                    AppUtils.print("请输入车辆库存（辆）:");
                    vehicle.setVehicleStock(scanner.nextInt());
                }
                case 5 -> {
                    if (vehicleType.equals("commercial")) {
                        AppUtils.print("请输入车辆载重（吨）:");
                        ((CommercialVehicleEntity) vehicle).setLoadCapacity(scanner.nextDouble());
                    } else if (vehicleType.equals("passenger")) {
                        AppUtils.print("请输入车辆载客量（座）:");
                        ((PassengerVehicleEntity) vehicle).setSeatCount(scanner.nextInt());
                    }
                }
                case 6 -> {
                    if (vehicleType.equals("commercial")) {
                        AppUtils.print("请输入车辆容积（立方米）:");
                        ((CommercialVehicleEntity) vehicle).setCargoVolume(scanner.nextDouble());
                    } else if (vehicleType.equals("passenger")) {
                        AppUtils.print("请输入车辆动力类型:");
                        ((PassengerVehicleEntity) vehicle).setFuelType(scanner.next());
                    }
                }
                case 7 -> {
                    printVehicle(vehicle);
                    AppUtils.print("是否确定修改车辆信息？(y/n)");
                    if (scanner.next().equals("y")) {
                        if (vehicleService.updateVehicle(vehicle) > -1) {
                            AppUtils.print("修改成功！");
                        } else {
                            AppUtils.print("修改失败！");
                        }
                        return;
                    } else {
                        AppUtils.print("取消修改！");
                    }
                }
                case 0 -> {
                    AppUtils.print("未保存的修改将会丢失！确定取消？（y/n）");
                    if (scanner.next().equals("y")) {
                        AppUtils.print("取消修改！");
                        return;
                    }
                }
                default -> AppUtils.print("请输入正确的选项！");
            }
        }
    }


    /**
     * 详细打印车辆信息
     *
     * @param vehicle 车辆
     */
    @Override
    public void printVehicle(VehicleEntity vehicle) {
        AppUtils.print("车辆详细信息:");
        AppUtils.printLine();
        AppUtils.print("车辆ID: %d", vehicle.getVehicleId());
        AppUtils.print("车辆类型: %s", vehicle.getVehicleType());
        AppUtils.print("车辆品牌: %s", vehicle.getVehicleBrand());
        AppUtils.print("车辆型号: %s", vehicle.getVehicleModel());
        AppUtils.print("车辆单价: %.2f万元", vehicle.getVehiclePrice());
        AppUtils.print("车辆库存: %d辆", vehicle.getVehicleStock());

        if (vehicle instanceof CommercialVehicleEntity commercialVehicle) {
            AppUtils.print("载重: %.2f吨", commercialVehicle.getLoadCapacity());
            AppUtils.print("容积: %.2f立方米", commercialVehicle.getCargoVolume());
        } else if (vehicle instanceof PassengerVehicleEntity passengerVehicle) {
            AppUtils.print("座位数: %d座", passengerVehicle.getSeatCount());
            AppUtils.print("动力类型: %s", passengerVehicle.getFuelType());
        }
        AppUtils.printLine();
    }

    /**
     * 行内打印车辆信息
     *
     * @param vehicle 车辆
     */
    @Override
    public void printVehicleInline(VehicleEntity vehicle) {
        AppUtils.print(vehicle.toString());
    }

    @Override
    public void printAllVehicle(ArrayList<VehicleEntity> vehicles) {
        for (VehicleEntity vehicle : vehicles) {
            printVehicleInline(vehicle);
        }
    }
    
    /**
     * 加入购物车功能
     */
    private void addToCart() {
        if (currentUser == null) {
            AppUtils.print("请先登录！");
            return;
        }
        
        ArrayList<VehicleEntity> vehicles = vehicleService.selectAllVehicle();
        AppUtils.printDoubleLine();
        AppUtils.print("车辆列表：");
        AppUtils.printLine();
        printAllVehicle(vehicles);
        AppUtils.printLine();
        
        AppUtils.print("请输入要加入购物车的车辆ID：");
        long vehicleId = scanner.nextLong();
        VehicleEntity vehicle = vehicleService.selectVehicle(vehicleId);
        
        if (vehicle == null) {
            AppUtils.print("车辆不存在！");
            return;
        }
        
        if (vehicle.getVehicleStock() <= 0) {
            AppUtils.print("车辆已售完！");
            return;
        }
        
        AppUtils.print("请输入购买数量（库存：%d）：", vehicle.getVehicleStock());
        int quantity = scanner.nextInt();
        
        if (quantity <= 0) {
            AppUtils.print("购买数量必须大于0！");
            return;
        }
        
        if (quantity > vehicle.getVehicleStock()) {
            AppUtils.print("库存不足！");
            return;
        }
        
        // 调用购物车服务添加商品
        cn.javat.javaLearn.experiment4.service.CartService cartService = cn.javat.javaLearn.experiment4.service.Impl.CartServiceImpl.getInstance();
        int result = cartService.addToCart(currentUser.getUserId(), vehicleId, quantity);
        
        if (result == 0) {
            AppUtils.print("车辆已成功加入购物车！");
        } else {
            AppUtils.print("加入购物车失败！");
        }
    }
    
    /**
     * 直接添加到购物车（从购买界面）
     */
    private void addToCartDirect(VehicleEntity vehicle) {
        AppUtils.print("请输入购买数量（库存：%d）：", vehicle.getVehicleStock());
        int quantity = scanner.nextInt();
        
        if (quantity <= 0) {
            AppUtils.print("购买数量必须大于0！");
            return;
        }
        
        if (quantity > vehicle.getVehicleStock()) {
            AppUtils.print("库存不足！");
            return;
        }
        
        // 调用购物车服务添加商品
        cn.javat.javaLearn.experiment4.service.CartService cartService = cn.javat.javaLearn.experiment4.service.Impl.CartServiceImpl.getInstance();
        int result = cartService.addToCart(currentUser.getUserId(), vehicle.getVehicleId(), quantity);
        
        if (result == 0) {
            AppUtils.print("车辆已成功加入购物车！");
        } else {
            AppUtils.print("加入购物车失败！");
        }
    }
    
    @Override
    public void searchVehicle() {
        AppUtils.printDoubleLine();
        AppUtils.print("车辆搜索功能");
        AppUtils.printLine();
        AppUtils.print("1. 关键词搜索");
        AppUtils.print("2. 条件筛选");
        AppUtils.print("0. 返回上级");
        
        switch (scanner.nextInt()) {
            case 1 -> {
                AppUtils.print("请输入搜索关键词（品牌或型号）:");
                scanner.nextLine(); // 消费掉nextInt()后的换行符
                String keyword = scanner.nextLine();
                ArrayList<VehicleEntity> result = vehicleService.searchByKeyword(keyword);
                if (result.isEmpty()) {
                    AppUtils.print("未找到匹配的车辆信息！");
                } else {
                    AppUtils.print("搜索结果:");
                    AppUtils.printLine();
                    printAllVehicle(result);
                    // 添加操作选项
                    handleSearchResult(result);
                }
            }
            case 2 -> {
                scanner.nextLine(); // 消费掉nextInt()后的换行符
                
                AppUtils.print("请输入品牌（留空表示不限）:");
                String brand = scanner.nextLine();
                if (brand.trim().isEmpty()) brand = null;
                
                AppUtils.print("请输入最低价格（留空表示不限）:");
                String minPriceStr = scanner.nextLine();
                Double minPrice = null;
                if (!minPriceStr.trim().isEmpty()) {
                    try {
                        minPrice = Double.valueOf(minPriceStr);
                    } catch (NumberFormatException e) {
                        AppUtils.print("最低价格格式不正确，将忽略此条件");
                    }
                }
                
                AppUtils.print("请输入最高价格（留空表示不限）:");
                String maxPriceStr = scanner.nextLine();
                Double maxPrice = null;
                if (!maxPriceStr.trim().isEmpty()) {
                    try {
                        maxPrice = Double.valueOf(maxPriceStr);
                    } catch (NumberFormatException e) {
                        AppUtils.print("最高价格格式不正确，将忽略此条件");
                    }
                }
                
                AppUtils.print("请输入车辆类型（passenger:乘用车, commercial:商用车, 留空表示不限）:");
                String type = scanner.nextLine();
                if (type.trim().isEmpty()) type = null;
                
                ArrayList<VehicleEntity> result = vehicleService.searchByCondition(brand, minPrice, maxPrice, type);
                if (result.isEmpty()) {
                    AppUtils.print("未找到匹配的车辆信息！");
                } else {
                    AppUtils.print("筛选结果:");
                    AppUtils.printLine();
                    printAllVehicle(result);
                    // 添加操作选项
                    handleSearchResult(result);
                }
            }
            case 0 -> {
                // 返回上一级
                scanner.nextLine(); // 消费掉换行符
            }
            default -> {
                AppUtils.print("请输入正确的选项！");
                scanner.nextLine(); // 消费掉换行符
            }
        }
    }
    
    @Override
    public void showLowStockVehicles() {
        AppUtils.printDoubleLine();
        AppUtils.print("库存预警功能");
        AppUtils.printLine();
        AppUtils.print("请输入库存阈值（显示库存小于等于该值的车辆）:");
        int threshold = scanner.nextInt();
        
        ArrayList<VehicleEntity> lowStockVehicles = vehicleService.getLowStockVehicles(threshold);
        
        if (lowStockVehicles.isEmpty()) {
            AppUtils.print("没有库存低于 %d 的车辆", threshold);
        } else {
            AppUtils.print("以下车辆库存低于或等于 %d:", threshold);
            AppUtils.printLine();
            printAllVehicle(lowStockVehicles);
        }
    }
    
    @Override
    public void showInventoryStatistics() {
        AppUtils.printDoubleLine();
        AppUtils.print("库存统计功能");
        AppUtils.printLine();
        
        double turnoverRate = vehicleService.calculateInventoryTurnoverRate();
        AppUtils.print("库存周转率: %.2f", turnoverRate);
        
        if (turnoverRate < 0.5) {
            AppUtils.print("提示: 库存周转率较低，建议采取促销措施");
        } else if (turnoverRate > 2.0) {
            AppUtils.print("提示: 库存周转率较高，注意及时补货");
        } else {
            AppUtils.print("提示: 库存周转率正常");
        }
    }
    
    /**
     * 处理搜索结果，提供操作选项
     */
    private void handleSearchResult(ArrayList<VehicleEntity> vehicles) {
        if (vehicles.isEmpty()) {
            return;
        }
        
        AppUtils.printLine();
        AppUtils.print("请选择操作：");
        AppUtils.print("1. 查看车辆详情");
        AppUtils.print("0. 返回上级");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                AppUtils.print("请输入要查看的车辆序号（1-%d）：", vehicles.size());
                int index = scanner.nextInt();
                if (index < 1 || index > vehicles.size()) {
                    AppUtils.print("序号无效！");
                    return;
                }
                
                VehicleEntity vehicle = vehicles.get(index - 1);
                AppUtils.printDoubleLine();
                AppUtils.print("车辆详细信息：");
                printVehicle(vehicle);
                AppUtils.printLine();
                
                AppUtils.print("请选择操作：");
                AppUtils.print("1. 立即购买");
                AppUtils.print("2. 加入购物车");
                AppUtils.print("0. 返回上级");
                int action = scanner.nextInt();
                switch (action) {
                    case 1 -> performPurchase(vehicle);
                    case 2 -> addToCartDirect(vehicle);
                    case 0 -> {
                        return;
                    }
                    default -> AppUtils.print("无效选择！");
                }
            }
            case 0 -> {
                return;
            }
            default -> AppUtils.print("无效选择！");
        }
    }

}