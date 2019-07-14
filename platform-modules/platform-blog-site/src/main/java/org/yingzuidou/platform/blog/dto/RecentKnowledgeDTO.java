package org.yingzuidou.platform.blog.dto;

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
public class RecentKnowledgeDTO {

    private String knowledgeName;

    private Integer knowledgeId;

    private String categoryName;

    private Integer categoryId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentKnowledgeDTO that = (RecentKnowledgeDTO) o;
        return Objects.equals(knowledgeName, that.knowledgeName) &&
                Objects.equals(knowledgeId, that.knowledgeId) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(knowledgeName, knowledgeId, categoryName, categoryId);
    }
}
