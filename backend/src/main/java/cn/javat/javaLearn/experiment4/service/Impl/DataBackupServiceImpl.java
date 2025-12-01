package cn.javat.javaLearn.experiment4.service.Impl;

import cn.javat.javaLearn.experiment4.config.AppConfig;
import cn.javat.javaLearn.experiment4.service.DataBackupService;
import cn.javat.javaLearn.experiment4.utils.AppUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBackupServiceImpl implements DataBackupService {

    private static volatile DataBackupServiceImpl instance;

    private DataBackupServiceImpl() {
    }

    public static DataBackupServiceImpl getInstance() {
        if (instance == null) {
            synchronized (DataBackupServiceImpl.class) {
                if (instance == null) {
                    instance = new DataBackupServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * 获取MySQL工具的完整路径
     *
     * @param toolName 工具名称 (mysqldump 或 mysql)
     * @return 工具的完整路径
     */
    private String getMysqlToolPath(String toolName) {
        AppConfig config = AppConfig.getInstance();
        String mysqlBinPath = config.getProperty("mysql.bin_path", "").trim();

        // 如果配置了MySQL bin路径，则使用配置的路径
        if (!mysqlBinPath.isEmpty()) {
            // 根据操作系统添加适当的路径分隔符和文件扩展名
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                return mysqlBinPath + File.separator + toolName + ".exe";
            } else {
                return mysqlBinPath + File.separator + toolName;
            }
        }

        // 如果没有配置路径，则直接使用工具名称（依赖系统PATH）
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            return toolName + ".exe";
        } else {
            return toolName;
        }
    }

    /**
     * 执行数据库备份
     *
     * @return 备份结果 true: 成功 false: 失败
     */
    @Override
    public boolean backupDatabase() {
        try {
            AppConfig config = AppConfig.getInstance();
            String username = config.getProperty("database.username");
            String password = config.getProperty("database.password");
            String databaseUrl = config.getProperty("database.url");

            // 从数据库URL中提取主机、端口和数据库名
            // jdbc:mysql://localhost:3306/car_system
            String[] urlParts = databaseUrl.split("//")[1].split(":");
            String host = urlParts[0];
            String portAndDb = urlParts[1]; // 3306/car_system
            String[] portAndDbParts = portAndDb.split("/");
            String port = portAndDbParts[0];
            String database = portAndDbParts[1];

            // 获取备份保存路径
            String backupPath = config.getProperty("backup.save_path", "data/backup");

            // 确保备份目录存在
            File backupDir = new File(backupPath);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // 生成备份文件名
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String backupFileName = String.format("backup_%s.sql", timestamp);
//            构建路径
            String backupFilePath = backupPath + File.separator + backupFileName;

            AppUtils.print("开始数据库备份，请稍候...");
            AppUtils.print("备份文件将保存到：%s", backupFilePath);

            // 获取mysqldump的完整路径
            String mysqldumpPath = getMysqlToolPath("mysqldump");

            // 构建mysqldump命令
            ProcessBuilder processBuilder = new ProcessBuilder();
//            mysqldump -h<host> -P<port> -u<username> -p<password> <database>
            processBuilder.command(mysqldumpPath, "-h" + host, "-P" + port, "-u" + username, "-p" + password, database);

            // 重定向输出到文件
            processBuilder.redirectOutput(new File(backupFilePath));

            // 启动进程
            Process process = processBuilder.start();

            AppUtils.print("正在执行备份操作，请勿关闭程序...");

            // 等待进程完成（这里自然实现了锁定）
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                File backupFile = new File(backupFilePath);
                AppUtils.print("数据库备份成功！备份文件：%s，大小：%.2f KB", backupFilePath, backupFile.length() / 1024.0);
                return true;
            } else {
                AppUtils.print("数据库备份失败！错误代码：%d", exitCode);

                // 读取错误输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    AppUtils.print("错误信息：%s", line);
                }
                return false;
            }
        } catch (Exception e) {
            AppUtils.print("数据库备份过程中发生异常：%s", e.getMessage());
            return false;
        }
    }

    /**
     * 从备份文件恢复数据库
     *
     * @param backupFilePath 备份文件路径
     * @return 恢复结果 true: 成功 false: 失败
     */
    @Override
    public boolean restoreDatabase(String backupFilePath) {
        try {
            AppConfig config = AppConfig.getInstance();
            String username = config.getProperty("database.username");
            String password = config.getProperty("database.password");
            String databaseUrl = config.getProperty("database.url");

            // 从数据库URL中提取主机、端口和数据库名
            String[] urlParts = databaseUrl.split("//")[1].split(":");
            String host = urlParts[0];
            String portAndDb = urlParts[1];
            String[] portAndDbParts = portAndDb.split("/");
            String port = portAndDbParts[0];
            String database = portAndDbParts[1];

            // 检查备份文件是否存在
            File backupFile = new File(backupFilePath);
            if (!backupFile.exists()) {
                AppUtils.print("备份文件不存在：%s", backupFilePath);
                return false;
            }

            AppUtils.print("开始数据库恢复，请稍候...");
            AppUtils.print("正在使用备份文件：%s", backupFilePath);

            // 获取mysql的完整路径
            String mysqlPath = getMysqlToolPath("mysql");

            // 构建mysql命令
            ProcessBuilder processBuilder = new ProcessBuilder();
//            mysql -h<host> -P<port> -u<username> -p<password> <database>

            processBuilder.command(mysqlPath, "-h" + host, "-P" + port, "-u" + username, "-p" + password, database);

            // 重定向输入从文件
            processBuilder.redirectInput(new File(backupFilePath));

            // 启动进程
            Process process = processBuilder.start();

            AppUtils.print("正在执行恢复操作，请勿关闭程序...");

            // 等待进程完成（这里自然实现了锁定）
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                AppUtils.print("数据库恢复成功！使用备份文件：%s", backupFilePath);
                return true;
            } else {
                AppUtils.print("数据库恢复失败！错误代码：%d", exitCode);

                // 读取错误输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    AppUtils.print("错误信息：%s", line);
                }
                return false;
            }
        } catch (Exception e) {
            AppUtils.print("数据库恢复过程中发生异常：%s", e.getMessage());
            return false;
        }
    }

    /**
     * 列出所有备份文件
     *
     * @return 备份文件列表
     */
    @Override
    public File[] listBackupFiles() {
        AppConfig config = AppConfig.getInstance();
        String backupPath = config.getProperty("backup.save_path", "data/backup");

        File backupDir = new File(backupPath);
        if (!backupDir.exists()) {
            return new File[0];
        }

        return backupDir.listFiles((dir, name) -> name.endsWith(".sql"));
    }
}