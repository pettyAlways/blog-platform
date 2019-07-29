package org.yingzuidou.platform.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Date;
import java.util.List;
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
public class CommentDTO {

    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 评论人ID
     */
    private Integer commentUserId;

    /**
     * 评论人名字
     */
    private String commentUserName;

    /**
     * 评论人头像
     */
    private String commentUserAvatar;

    /**
     * 评论时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commentTime;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论下所有回复
     */
    List<ReplyDTO> replyList;


    public static Function<Object[], CommentDTO> articleCommentList = data -> new CommentDTO()
            .setCommentUserId(CmsBeanUtils.objectToInt(data[0])).setCommentUserName(CmsBeanUtils.objectToString(data[1]))
            .setCommentUserAvatar(CmsBeanUtils.objectToString(data[2]))
            .setCommentTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[3]), "yyyy-MM-dd HH:mm:ss"))
            .setCommentContent(CmsBeanUtils.objectToString(data[4])).setCommentId(CmsBeanUtils.objectToInt(data[5]));


}
