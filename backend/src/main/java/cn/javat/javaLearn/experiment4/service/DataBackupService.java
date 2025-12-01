package cn.javat.javaLearn.experiment4.service;

import java.io.File;

public interface DataBackupService {
    /**
     * 执行数据库备份
     *
     * @return 备份结果 true: 成功 false: 失败
     */
    boolean backupDatabase();

    /**
     * 从备份文件恢复数据库
     *
     * @param backupFilePath 备份文件路径
     * @return 恢复结果 true: 成功 false: 失败
     */
    boolean restoreDatabase(String backupFilePath);

    /**
     * 列出所有备份文件
     *
     * @return 备份文件列表
     */
    File[] listBackupFiles();
}