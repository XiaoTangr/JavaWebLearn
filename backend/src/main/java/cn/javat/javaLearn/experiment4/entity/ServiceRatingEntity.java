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
public class ServiceRatingEntity {
    private long id;
    private long serviceId;
    private long userId;
    private int rating; // 评分 1-5
    private String comment;
    private long createTime;

    @Override
    public String toString() {
        return String.format("<%d> 服务ID：%d 用户ID：%d 评分：%d星 评论：%s 创建时间：%s",
                getId(),
                getServiceId(),
                getUserId(),
                getRating(),
                getComment() != null ? getComment() : "无",
                new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(getCreateTime())
        );
    }
}