package cn.javat.javaLearn.experiment4.controller.Impl;

import cn.javat.javaLearn.experiment4.config.AppConfig;
import cn.javat.javaLearn.experiment4.controller.DataBackupController;
import cn.javat.javaLearn.experiment4.entity.UserEntity;
import cn.javat.javaLearn.experiment4.service.DataBackupService;
import cn.javat.javaLearn.experiment4.service.Impl.DataBackupServiceImpl;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class DataBackupControllerImpl implements DataBackupController {
    
    private final String ADMIN_EMAIL = AppConfig.getInstance().getProperty("user.admin_email");
    private final Scanner scanner = new Scanner(System.in);
    private final DataBackupService dataBackupService = DataBackupServiceImpl.getInstance();
    
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
                AppUtils.print("您没有权限访问数据备份功能！");
            }
        } else {
            AppUtils.print("请先登录！");
        }
    }

    private boolean isAdmin() {
        return Objects.equals(currentUser.getUserEmail(), ADMIN_EMAIL);
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
            AppUtils.print("数据备份系统 - 管理员面板");
            AppUtils.printLine();
            AppUtils.print("1. 执行数据库备份");
            AppUtils.print("2. 查看备份文件列表");
            AppUtils.print("3. 恢复数据库");
            AppUtils.print("0. 返回上级");
            AppUtils.printLine();
            AppUtils.print("请选择操作：");
            
            switch (scanner.nextInt()) {
                case 1 -> performBackup();
                case 2 -> listBackupFiles();
                case 3 -> restoreBackup();
                case 0 -> {
                    currentUser = null;
                    return;
                }
                default -> AppUtils.print("无效的选择！请重新选择。");
            }
        }
    }

    /**
     * 执行数据库备份
     */
    private void performBackup() {
        AppUtils.printDoubleLine();
        AppUtils.print("执行数据库备份");
        AppUtils.printLine();
        
        AppUtils.print("确认执行数据库备份？(y/n)");
        String confirm = scanner.next();
        if (confirm.equals("y")) {
            boolean result = dataBackupService.backupDatabase();
            if (result) {
                AppUtils.print("数据库备份完成！");
            } else {
                AppUtils.print("数据库备份失败！");
            }
        } else {
            AppUtils.print("取消数据库备份！");
        }
    }

    /**
     * 列出备份文件
     */
    private void listBackupFiles() {
        AppUtils.printDoubleLine();
        AppUtils.print("备份文件列表");
        AppUtils.printLine();
        
        File[] backupFiles = dataBackupService.listBackupFiles();
        if (backupFiles.length == 0) {
            AppUtils.print("暂无备份文件！");
            return;
        }
        
        for (int i = 0; i < backupFiles.length; i++) {
            AppUtils.print("%d. %s (%.2f KB)", 
                i + 1, 
                backupFiles[i].getName(), 
                backupFiles[i].length() / 1024.0);
        }
    }

    /**
     * 恢复数据库
     */
    private void restoreBackup() {
        AppUtils.printDoubleLine();
        AppUtils.print("恢复数据库");
        AppUtils.printLine();
        
        File[] backupFiles = dataBackupService.listBackupFiles();
        if (backupFiles.length == 0) {
            AppUtils.print("暂无备份文件可供恢复！");
            return;
        }
        
        AppUtils.print("备份文件列表：");
        for (int i = 0; i < backupFiles.length; i++) {
            AppUtils.print("%d. %s (%.2f KB)", 
                i + 1, 
                backupFiles[i].getName(), 
                backupFiles[i].length() / 1024.0);
        }
        
        AppUtils.print("请选择要恢复的备份文件（输入序号）：");
        int choice = scanner.nextInt();
        if (choice < 1 || choice > backupFiles.length) {
            AppUtils.print("无效选择！");
            return;
        }
        
        File selectedFile = backupFiles[choice - 1];
        
        AppUtils.print("警告：此操作将覆盖当前数据库内容！");
        AppUtils.print("确认从备份文件 %s 恢复数据库？(y/n)", selectedFile.getName());
        String confirm = scanner.next();
        if (confirm.equals("y")) {
            boolean result = dataBackupService.restoreDatabase(selectedFile.getAbsolutePath());
            if (result) {
                AppUtils.print("数据库恢复完成！");
            } else {
                AppUtils.print("数据库恢复失败！");
            }
        } else {
            AppUtils.print("取消数据库恢复！");
        }
    }
}