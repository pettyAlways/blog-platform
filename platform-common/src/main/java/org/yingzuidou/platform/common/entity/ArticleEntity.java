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
@NamedEntityGraph(name = "Article.Graph", attributeNodes = {
        @NamedAttributeNode("author"), @NamedAttributeNode("knowledge"), @NamedAttributeNode("creator"),
        @NamedAttributeNode("updator"), @NamedAttributeNode("participantList")
})
public class ArticleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "article_title")
    private String articleTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private CmsUserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    private KnowledgeEntity knowledge;

    @Basic
    @Column(name = "post_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;

    @Basic
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private CmsUserEntity creator;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updator")
    private CmsUserEntity updator;

    @JoinTable(name="article_participant",
            joinColumns={@JoinColumn(name="article_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id")}
    )
    @ManyToMany
    private List<CmsUserEntity> participantList;

    @Basic
    @Column(name = "update_time")
    private Date updateTime;

    @Basic
    @Column(name = "is_delete", insertable = false)
    private String isDelete;

}
