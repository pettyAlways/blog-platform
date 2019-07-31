package org.yingzuidou.platform.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.MessageEntity;

import java.util.Date;
import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/18
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class MessageDTO {

    /**
     * 消息ID
     */
    private Integer messageId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    List<MessageEntity> unReadMessages;

    List<MessageEntity> readMessages;

    List<MessageEntity> auditMessages;
}
