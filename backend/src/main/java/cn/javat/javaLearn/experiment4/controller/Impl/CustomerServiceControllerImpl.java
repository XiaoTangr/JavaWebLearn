package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.config.AppConfig;
import cn.javat.javaLearn.experiment4.controller.CustomerServiceController;
import cn.javat.javaLearn.experiment4.entity.CustomerServiceEntity;
import cn.javat.javaLearn.experiment4.entity.ServiceRatingEntity;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.service.Impl.CustomerServiceServiceImpl;
import cn.javat.javaLearn.experiment4.service.CustomerServiceService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CustomerServiceControllerImpl implements CustomerServiceController {
    
    private final String ADMIN_EMAIL = AppConfig.getInstance().getProperty("user.admin_email");
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerServiceService customerServiceService = CustomerServiceServiceImpl.getInstance();
    
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

    @Override
    public void userPanel() {
        if (currentUser == null) {
            AppUtils.print("请先登录！");
            return;
        }
        
        while (currentUser != null) {
            AppUtils.printDoubleLine();
            AppUtils.print("客户服务系统 - 用户面板");
            AppUtils.printLine();
            AppUtils.print("1. 提交咨询");
            AppUtils.print("2. 提交投诉/建议");
            AppUtils.print("3. 查看我的服务请求");
            AppUtils.print("4. 评价已完成的服务");
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            
            switch (scanner.nextInt()) {
                case 1 -> submitConsultation();
                case 2 -> submitComplaint();
                case 3 -> viewMyServices();
                case 4 -> rateService();
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    @Override
    public void adminPanel() {
        if (currentUser == null) {
            AppUtils.print("请先登录！");
            return;
        }
        
        if (!isAdmin()) {
            AppUtils.print("您没有权限访问此功能！");
            return;
        }
        
        while (currentUser != null) {
            AppUtils.printDoubleLine();
            AppUtils.print("客户服务系统 - 管理员面板");
            AppUtils.printLine();
            AppUtils.print("1. 查看所有服务请求");
            AppUtils.print("2. 按类型查看服务请求");
            AppUtils.print("3. 按状态查看服务请求");
            AppUtils.print("4. 处理服务请求");
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            
            switch (scanner.nextInt()) {
                case 1 -> viewAllServices();
                case 2 -> viewServicesByType();
                case 3 -> viewServicesByStatus();
                case 4 -> processService();
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    // 用户功能方法
    private void submitConsultation() {
        AppUtils.printDoubleLine();
        AppUtils.print("提交咨询");
        AppUtils.printLine();
        
        CustomerServiceEntity service = new CustomerServiceEntity();
        service.setUserId(currentUser.getUserId());
        service.setType("consultation");
        service.setStatus("open");
        
        AppUtils.print("请输入咨询主题：");
        scanner.nextLine(); // 消费换行符
        service.setSubject(scanner.nextLine());
        
        AppUtils.print("请输入咨询内容：");
        service.setContent(scanner.nextLine());
        
        AppUtils.print("请选择优先级 (1-低 2-中 3-高)：");
        int priorityChoice = scanner.nextInt();
        switch (priorityChoice) {
            case 1 -> service.setPriority("low");
            case 2 -> service.setPriority("medium");
            case 3 -> service.setPriority("high");
            default -> {
                service.setPriority("medium");
                AppUtils.print("无效选择，使用默认优先级：中");
            }
        }
        
        long currentTime = System.currentTimeMillis();
        service.setCreateTime(currentTime);
        service.setUpdateTime(currentTime);
        
        AppUtils.print("确认提交咨询？(y/n)");
        String confirm = scanner.next();
        if (confirm.equals("y")) {
            int result = customerServiceService.createService(service);
            if (result == 0) {
                AppUtils.print("咨询提交成功！");
            } else {
                AppUtils.print("咨询提交失败！");
            }
        } else {
            AppUtils.print("取消提交咨询！");
        }
    }

    private void submitComplaint() {
        AppUtils.printDoubleLine();
        AppUtils.print("提交投诉/建议");
        AppUtils.printLine();
        
        CustomerServiceEntity service = new CustomerServiceEntity();
        service.setUserId(currentUser.getUserId());
        service.setType("complaint");
        service.setStatus("open");
        
        AppUtils.print("请输入主题：");
        scanner.nextLine(); // 消费换行符
        service.setSubject(scanner.nextLine());
        
        AppUtils.print("请输入内容：");
        service.setContent(scanner.nextLine());
        
        AppUtils.print("请选择类型 (1-投诉 2-建议)：");
        int typeChoice = scanner.nextInt();
        if (typeChoice == 1) {
            service.setType("complaint");
        } else {
            service.setType("feedback");
        }
        
        AppUtils.print("请选择优先级 (1-低 2-中 3-高)：");
        int priorityChoice = scanner.nextInt();
        switch (priorityChoice) {
            case 1 -> service.setPriority("low");
            case 2 -> service.setPriority("medium");
            case 3 -> service.setPriority("high");
            default -> {
                service.setPriority("medium");
                AppUtils.print("无效选择，使用默认优先级：中");
            }
        }
        
        long currentTime = System.currentTimeMillis();
        service.setCreateTime(currentTime);
        service.setUpdateTime(currentTime);
        
        AppUtils.print("确认提交？(y/n)");
        String confirm = scanner.next();
        if (confirm.equals("y")) {
            int result = customerServiceService.createService(service);
            if (result == 0) {
                AppUtils.print("提交成功！");
            } else {
                AppUtils.print("提交失败！");
            }
        } else {
            AppUtils.print("取消提交！");
        }
    }

    private void viewMyServices() {
        ArrayList<CustomerServiceEntity> services = customerServiceService.selectServicesByUserId(currentUser.getUserId());
        if (services.isEmpty()) {
            AppUtils.print("您还没有提交任何服务请求！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("我的服务请求");
        AppUtils.printLine();
        for (CustomerServiceEntity service : services) {
            AppUtils.print(service.toString());
        }
    }

    private void rateService() {
        ArrayList<CustomerServiceEntity> services = customerServiceService.selectServicesByUserId(currentUser.getUserId());
        if (services.isEmpty()) {
            AppUtils.print("您还没有提交任何服务请求！");
            return;
        }
        
        // 只显示已解决或已关闭的服务
        ArrayList<CustomerServiceEntity> completedServices = new ArrayList<>();
        for (CustomerServiceEntity service : services) {
            if ("resolved".equals(service.getStatus()) || "closed".equals(service.getStatus())) {
                completedServices.add(service);
            }
        }
        
        if (completedServices.isEmpty()) {
            AppUtils.print("您没有已完成的服务可以评价！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("可评价的服务");
        AppUtils.printLine();
        for (int i = 0; i < completedServices.size(); i++) {
            AppUtils.print("%d. %s", i + 1, completedServices.get(i).toString());
        }
        
        AppUtils.print("请选择要评价的服务（输入序号）：");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > completedServices.size()) {
            AppUtils.print("无效选择！");
            return;
        }
        
        CustomerServiceEntity selectedService = completedServices.get(choice - 1);
        
        ServiceRatingEntity rating = new ServiceRatingEntity();
        rating.setServiceId(selectedService.getId());
        rating.setUserId(currentUser.getUserId());
        rating.setCreateTime(System.currentTimeMillis());
        
        AppUtils.print("请评分（1-5星）：");
        int score = scanner.nextInt();
        if (score < 1 || score > 5) {
            AppUtils.print("评分应在1-5星之间！");
            return;
        }
        rating.setRating(score);
        
        AppUtils.print("请输入评价内容（可选）：");
        scanner.nextLine(); // 消费换行符
        rating.setComment(scanner.nextLine());
        
        int result = customerServiceService.addRating(rating);
        if (result == 0) {
            AppUtils.print("评价提交成功！");
        } else {
            AppUtils.print("评价提交失败！");
        }
    }

    // 管理员功能方法
    private void viewAllServices() {
        ArrayList<CustomerServiceEntity> services = customerServiceService.selectAllServices();
        if (services.isEmpty()) {
            AppUtils.print("暂无服务请求！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("所有服务请求");
        AppUtils.printLine();
        for (CustomerServiceEntity service : services) {
            AppUtils.print(service.toString());
        }
    }

    private void viewServicesByType() {
        AppUtils.print("请选择服务类型 (1-咨询 2-投诉 3-建议)：");
        int typeChoice = scanner.nextInt();
        String type;
        switch (typeChoice) {
            case 1 -> type = "consultation";
            case 2 -> type = "complaint";
            case 3 -> type = "feedback";
            default -> {
                AppUtils.print("无效选择！");
                return;
            }
        }
        
        ArrayList<CustomerServiceEntity> services = customerServiceService.selectServicesByType(type);
        if (services.isEmpty()) {
            AppUtils.print("暂无此类服务请求！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("%s服务请求", "consultation".equals(type) ? "咨询" : "complaint".equals(type) ? "投诉" : "建议");
        AppUtils.printLine();
        for (CustomerServiceEntity service : services) {
            AppUtils.print(service.toString());
        }
    }

    private void viewServicesByStatus() {
        AppUtils.print("请选择状态 (1-待处理 2-处理中 3-已解决 4-已关闭)：");
        int statusChoice = scanner.nextInt();
        String status;
        switch (statusChoice) {
            case 1 -> status = "open";
            case 2 -> status = "processing";
            case 3 -> status = "resolved";
            case 4 -> status = "closed";
            default -> {
                AppUtils.print("无效选择！");
                return;
            }
        }
        
        ArrayList<CustomerServiceEntity> services = customerServiceService.selectServicesByStatus(status);
        if (services.isEmpty()) {
            AppUtils.print("暂无此状态的服务请求！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("%s的服务请求", "open".equals(status) ? "待处理" : "processing".equals(status) ? "处理中" : "resolved".equals(status) ? "已解决" : "已关闭");
        AppUtils.printLine();
        for (CustomerServiceEntity service : services) {
            AppUtils.print(service.toString());
        }
    }

    private void processService() {
        ArrayList<CustomerServiceEntity> services = customerServiceService.selectAllServices();
        if (services.isEmpty()) {
            AppUtils.print("暂无服务请求！");
            return;
        }
        
        AppUtils.printDoubleLine();
        AppUtils.print("服务请求列表");
        AppUtils.printLine();
        for (int i = 0; i < services.size(); i++) {
            AppUtils.print("%d. %s", i + 1, services.get(i).toString());
        }
        
        AppUtils.print("请选择要处理的服务（输入序号）：");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > services.size()) {
            AppUtils.print("无效选择！");
            return;
        }
        
        CustomerServiceEntity selectedService = services.get(choice - 1);
        
        AppUtils.print("当前状态：%s", selectedService.getStatus());
        AppUtils.print("请选择新状态 (1-待处理 2-处理中 3-已解决 4-已关闭)：");
        int statusChoice = scanner.nextInt();
        String status;
        switch (statusChoice) {
            case 1 -> status = "open";
            case 2 -> status = "processing";
            case 3 -> status = "resolved";
            case 4 -> status = "closed";
            default -> {
                AppUtils.print("无效选择！");
                return;
            }
        }
        
        selectedService.setStatus(status);
        selectedService.setUpdateTime(System.currentTimeMillis());
        
        int result = customerServiceService.updateService(selectedService);
        if (result == 0) {
            AppUtils.print("服务状态更新成功！");
        } else {
            AppUtils.print("服务状态更新失败！");
        }
    }
}