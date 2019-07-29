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
@Table(name = "comment", schema = "cms_web", catalog = "")
public class CommentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评论文章ID
     */
    @Basic
    @Column(name = "article_id")
    private Integer articleId;

    /**
     * 评论内容
     */
    @Basic
    @Column(name = "comment_content")
    private String commentContent;

    /**
     * 评论者
     */
    @Basic
    @Column(name = "comment_user")
    private Integer commentUser;

    /**
     * 评论时间
     */
    @Basic
    @Column(name = "comment_time")
    private Date commentTime;

}
