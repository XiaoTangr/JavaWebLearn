package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    private long userId;
    private String userEmail;
    private String userName;
    private String passWord;
    private boolean active;


    @Override
    public String toString() {
        return String.format("<%s> 邮箱：%s 用户名：%s 状态：%s",
                getUserId(),
                getUserEmail(),
                getUserName(),
                isActive() ? "正常" : "禁用");
    }
}
