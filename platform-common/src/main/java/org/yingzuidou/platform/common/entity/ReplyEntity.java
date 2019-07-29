package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

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
@Entity
@Table(name = "reply", schema = "cms_web", catalog = "")
public class ReplyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评论ID
     */
    @Basic
    @Column(name = "comment_id")
    private Integer commentId;

    /**
     * 回复者
     */
    @Basic
    @Column(name = "reply_user")
    private Integer replyUser;

    /**
     * 回复内容
     */
    @Basic
    @Column(name = "reply_content")
    private String replyContent;

    /**
     * 回复时间
     */
    @Basic
    @Column(name = "reply_time")
    private Date replyTime;

    /**
     * 回复对象
     */
    @Basic
    @Column(name = "reply_obj")
    private Integer replyObj;

}
