package org.yingzuidou.cms.cmsweb.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * ResourceEntity
 *
 * @author shangguanls
 * @date 2018/9/27
 */
@Entity
@Table(name = "resource", schema = "my-cms", catalog = "")
public class ResourceEntity {

    private int id;
    private String resourceName;
    private Integer parentId;
    private String resourceType;
    private String inUse;
    private String resourcePath;
    private String resourceIcon;
    private Integer resourceSort;
    private int creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;
    private String isDelete;
    private String alias;
    private String defaultPage;

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
    @Column(name = "resource_name")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "resource_type")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "in_use")
    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }

    @Basic
    @Column(name = "resource_path")
    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Basic
    @Column(name = "resource_icon")
    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    @Basic
    @Column(name = "resource_sort")
    public Integer getResourceSort() {
        return resourceSort;
    }

    public void setResourceSort(Integer resourceSort) {
        this.resourceSort = resourceSort;
    }

    @Basic
    @Column(name = "creator")
    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
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
    @Column(name = "is_delete")
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceEntity that = (ResourceEntity) o;
        return id == that.id &&
                creator == that.creator &&
                Objects.equals(resourceName, that.resourceName) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(resourceType, that.resourceType) &&
                Objects.equals(inUse, that.inUse) &&
                Objects.equals(resourcePath, that.resourcePath) &&
                Objects.equals(resourceIcon, that.resourceIcon) &&
                Objects.equals(resourceSort, that.resourceSort) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updator, that.updator) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, resourceName, parentId, resourceType, inUse, resourcePath, resourceIcon, resourceSort, creator, createTime, updator, updateTime, isDelete);
    }

    @Basic
    @Column(name = "default_page")
    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }
}
