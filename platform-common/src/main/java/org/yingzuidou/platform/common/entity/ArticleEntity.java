package org.yingzuidou.platform.common.entity;


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
@Entity
@Table(name = "article", schema = "cms_web", catalog = "")
@NamedEntityGraph(name = "Article.Graph", attributeNodes = {
        @NamedAttributeNode("author"), @NamedAttributeNode("knowledge"), @NamedAttributeNode("creator"),
        @NamedAttributeNode("updator"), @NamedAttributeNode("participantList")
})
public class ArticleEntity {
    private int id;
    private String articleTitle;
    private CmsUserEntity author;
    private KnowledgeEntity knowledge;
    private Date postTime;
    private String content;
    private CmsUserEntity creator;
    private Date createTime;
    private CmsUserEntity updator;
    private List<CmsUserEntity> participantList;
    private Date updateTime;
    private String isDelete;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "article_title")
    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    public CmsUserEntity getAuthor() {
        return author;
    }

    public void setAuthor(CmsUserEntity author) {
        this.author = author;
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
    @Column(name = "post_time")
    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @JoinTable(name="article_participant",
            joinColumns={@JoinColumn(name="article_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id")}
    )
    @ManyToMany
    public List<CmsUserEntity> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<CmsUserEntity> participantList) {
        this.participantList = participantList;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    public CmsUserEntity getCreator() {
        return creator;
    }

    public void setCreator(CmsUserEntity creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updator")
    public CmsUserEntity getUpdator() {
        return updator;
    }

    public void setUpdator(CmsUserEntity updator) {
        this.updator = updator;
    }

    @Basic
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "is_delete", insertable = false)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return id == that.id &&
                creator == that.creator &&
                Objects.equals(articleTitle, that.articleTitle) &&
                Objects.equals(author, that.author) &&
                Objects.equals(knowledge, that.knowledge) &&
                Objects.equals(postTime, that.postTime) &&
                Objects.equals(content, that.content) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updator, that.updator) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, articleTitle, author, knowledge, postTime, content, creator, createTime, updator, updateTime, isDelete);
    }
}
