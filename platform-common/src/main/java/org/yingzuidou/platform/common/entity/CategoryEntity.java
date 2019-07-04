package org.yingzuidou.platform.common.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/3
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Entity
@Table(name = "category", schema = "cms_web", catalog = "")
public class CategoryEntity {
    private int id;
    @Length(max = 20, message = "分类名不能超过20个字符")
    private String categoryName;
    private String inUse;
    @Length(max = 20, message = "分类描述不能超过50个字符")
    private String categoryDesc;
    private Integer sort;
    private String isDelete;
    private Integer creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Basic
    @Column(name = "in_use", insertable = false)
    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }

    @Basic
    @Column(name = "creator")
    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
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

    @Basic
    @Column(name = "updator")
    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
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
    @Column(name = "category_desc")
    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Basic
    @Column(name = "is_delete", insertable = false)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return id == that.id &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(inUse, that.inUse) &&
                Objects.equals(categoryDesc, that.categoryDesc) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(isDelete, that.isDelete) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updator, that.updator) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, categoryName, inUse, categoryDesc, sort, isDelete, creator, createTime, updator, updateTime);
    }
}
