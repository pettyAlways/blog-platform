package org.yingzuidou.platform.blog.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Date;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/28
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class ReplyDTO {

    /**
     * 回复ID
     */
    private Integer replyId;

    /**
     * 回复人ID
     */
    private Integer replyUserId;

    /**
     * 回复人名字
     */
    private String replyUserName;

    /**
     * 回复时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 回复对象
     */
    private Integer replyObj;

    /**
     * 回复对象名字
     */
    private String replyObjName;


    /**
     * 评论下的回复列表转换
     */
    public static Function<Object[], ReplyDTO> articleReplyList = data -> new ReplyDTO()
            .setReplyUserId(CmsBeanUtils.objectToInt(data[0])).setReplyUserName(CmsBeanUtils.objectToString(data[1]))
            .setReplyTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setReplyContent(CmsBeanUtils.objectToString(data[3]))
            .setReplyObj(CmsBeanUtils.objectToInt(data[4])).setReplyObjName(CmsBeanUtils.objectToString(data[5]))
            .setReplyId(CmsBeanUtils.objectToInt(data[6]));
}
