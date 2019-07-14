package org.yingzuidou.platform.blog.dto;

import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class RecentAritcleDTO {

    private String articleTitle;

    private Integer articleId;

    private String knowledgeName;

    private Integer knowledgeId;

    private String categoryName;

    private Integer categoryId;

    private Date postTime;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public Integer getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentAritcleDTO that = (RecentAritcleDTO) o;
        return Objects.equals(articleTitle, that.articleTitle) &&
                Objects.equals(articleId, that.articleId) &&
                Objects.equals(knowledgeName, that.knowledgeName) &&
                Objects.equals(knowledgeId, that.knowledgeId) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(articleTitle, articleId, knowledgeName, knowledgeId, categoryName, categoryId);
    }
}
