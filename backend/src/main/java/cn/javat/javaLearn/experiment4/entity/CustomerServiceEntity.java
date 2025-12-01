package cn.javat.javaLearn.experiment4.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.text.SimpleDateFormat;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerServiceEntity {
    private long id;
    private long userId;
    private String type; // consultation, complaint, feedback
    private String subject;
    private String content;
    private String status; // open, processing, resolved, closed
    private String priority; // low, medium, high
    private long createTime;
    private Long updateTime;

    @Override
    public String toString() {
        return String.format("<%d> 类型：%s 主题：%s 状态：%s 优先级：%s 创建时间：%s",
                getId(),
                getType(),
                getSubject(),
                getStatus(),
                getPriority(),
                new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(getCreateTime())
        );
    }
}