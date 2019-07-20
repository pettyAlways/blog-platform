package org.yingzuidou.platform.common.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "article", schema = "cms_web", catalog = "")
public class ArticleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "article_title")
    private String articleTitle;

    @Basic
    @Column(name = "author_id")
    private Integer authorId;

    @Basic
    @Column(name = "knowledge_id")
    private Integer knowledgeId;

    @Basic
    @Column(name = "post_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "creator")
    private Integer creator;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "updator")
    private Integer updator;

    @Basic
    @Column(name = "update_time")
    private Date updateTime;

    @Basic
    @Column(name = "is_delete", insertable = false)
    private String isDelete;

}
