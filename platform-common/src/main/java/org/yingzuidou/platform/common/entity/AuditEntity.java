package org.yingzuidou.platform.common.entity;

import javax.persistence.*;
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
@Entity
@Table(name = "audit", schema = "cms_web", catalog = "")
public class AuditEntity {
    private int id;
    private Integer applyUser;
    private Integer handleUser;
    private String applyType;
    private Integer applyObj;
    private String handleResult;
    private Date handleTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "apply_user")
    public Integer getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Integer applyUser) {
        this.applyUser = applyUser;
    }

    @Basic
    @Column(name = "handle_user")
    public Integer getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(Integer handleUser) {
        this.handleUser = handleUser;
    }

    @Basic
    @Column(name = "apply_type")
    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    @Basic
    @Column(name = "apply_obj")
    public Integer getApplyObj() {
        return applyObj;
    }

    public void setApplyObj(Integer applyObj) {
        this.applyObj = applyObj;
    }

    @Basic
    @Column(name = "handle_result")
    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    @Basic
    @Column(name = "handle_time")
    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditEntity that = (AuditEntity) o;
        return id == that.id &&
                Objects.equals(applyUser, that.applyUser) &&
                Objects.equals(handleUser, that.handleUser) &&
                Objects.equals(applyType, that.applyType) &&
                Objects.equals(applyObj, that.applyObj) &&
                Objects.equals(handleResult, that.handleResult) &&
                Objects.equals(handleTime, that.handleTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, applyUser, handleUser, applyType, applyObj, handleResult, handleTime);
    }
}
