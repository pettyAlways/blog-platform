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
@Table(name = "oper_record", schema = "cms_web", catalog = "")
public class OperRecordEntity {
    private int id;
    private Integer operUser;
    private Date operTime;
    private String operType;
    private Integer handleUser;
    private String handleResult;
    private String objType;
    private Integer obj;
    private String rootType;
    private Integer rootObj;
    private String reserve;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "oper_user")
    public Integer getOperUser() {
        return operUser;
    }

    public void setOperUser(Integer operUser) {
        this.operUser = operUser;
    }

    @Basic
    @Column(name = "oper_time")
    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Basic
    @Column(name = "oper_type")
    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
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
    @Column(name = "handle_result")
    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    @Basic
    @Column(name = "obj_type")
    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    @Basic
    @Column(name = "obj")
    public Integer getObj() {
        return obj;
    }

    public void setObj(Integer obj) {
        this.obj = obj;
    }

    @Basic
    @Column(name = "root_type")
    public String getRootType() {
        return rootType;
    }

    public void setRootType(String rootType) {
        this.rootType = rootType;
    }

    @Basic
    @Column(name = "root_obj")
    public Integer getRootObj() {
        return rootObj;
    }

    public void setRootObj(Integer rootObj) {
        this.rootObj = rootObj;
    }

    @Basic
    @Column(name = "reserve")
    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperRecordEntity that = (OperRecordEntity) o;
        return id == that.id &&
                Objects.equals(operUser, that.operUser) &&
                Objects.equals(operTime, that.operTime) &&
                Objects.equals(operType, that.operType) &&
                Objects.equals(handleUser, that.handleUser) &&
                Objects.equals(handleResult, that.handleResult) &&
                Objects.equals(objType, that.objType) &&
                Objects.equals(obj, that.obj) &&
                Objects.equals(rootType, that.rootType) &&
                Objects.equals(rootObj, that.rootObj);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, operUser, operTime, operType, handleUser, handleResult, objType, obj, rootType, rootObj);
    }
}
