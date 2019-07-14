package org.yingzuidou.platform.common.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/14
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Entity
@Table(name = "recent_edit", schema = "cms_web", catalog = "")
@NamedEntityGraph(name = "RecentEdit.Graph", attributeNodes = {
        @NamedAttributeNode("article"), @NamedAttributeNode("knowledge")})
public class RecentEditEntity {
    private int id;
    private Integer userId;
    private ArticleEntity article;
    private KnowledgeEntity knowledge;
    private Date editTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    public ArticleEntity getArticle() {
        return article;
    }

    public void setArticle(ArticleEntity article) {
        this.article = article;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    public KnowledgeEntity getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(KnowledgeEntity knowledge) {
        this.knowledge = knowledge;
    }

    @Basic
    @Column(name = "edit_time")
    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentEditEntity that = (RecentEditEntity) o;
        return id == that.id &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(article, that.article) &&
                Objects.equals(knowledge, that.knowledge) &&
                Objects.equals(editTime, that.editTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, article, knowledge, editTime);
    }
}
