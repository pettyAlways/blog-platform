package org.yingzuidou.cms.cmsweb.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 组织实体类
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
@Entity
@Table(name = "organization", schema = "my-cms")
public class OrganizationEntity {
    private Integer id;
    private Integer parentId;
    private String orgName;
    private String expand;
    private Integer creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;
    private String isDelete;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "org_name")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Basic
    @Column(name = "expand")
    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    @Basic
    @Column(name = "creator", updatable = false)
    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "create_time", updatable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrganizationEntity that = (OrganizationEntity) o;

        if (id.intValue() != that.id) {
            return false;
        }
        if (creator.intValue() != that.creator) {
            return false;
        }
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) {
            return false;
        }
        if (expand != null ? !expand.equals(that.expand) : that.expand != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) {
            return false;
        }
        if (updator != null ? !updator.equals(that.updator) : that.updator != null) {
            return false;
        }
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) {
            return false;
        }
        return isDelete != null ? isDelete.equals(that.isDelete) : that.isDelete == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (expand != null ? expand.hashCode() : 0);
        result = 31 * result + creator;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updator != null ? updator.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }
}
