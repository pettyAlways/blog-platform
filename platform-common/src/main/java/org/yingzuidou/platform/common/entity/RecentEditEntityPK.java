package org.yingzuidou.platform.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
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
public class RecentEditEntityPK implements Serializable {
    private int id;
    private int articleId;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "article_id")
    @Id
    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecentEditEntityPK that = (RecentEditEntityPK) o;
        return id == that.id &&
                articleId == that.articleId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, articleId);
    }
}
