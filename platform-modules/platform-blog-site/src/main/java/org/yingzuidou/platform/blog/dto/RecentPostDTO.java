package org.yingzuidou.platform.blog.dto;

import java.util.Date;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class RecentPostDTO {

    private String operUserName;

    private Integer operUser;

    private Date operTime;

    private String operType;

    private String handleUserName;

    private Integer handleUser;

    private String handleResult;

    private String objType;

    private String objName;

    private Integer obj;

    private String reserve;

    /**
     * 在移除知识库用户时所指向的知识库名字
     */
    private String targetName;

    /**
     * 在移除知识库用户时所指向的知识库ID
     */
    private Integer target;

    public String getOperUserName() {
        return operUserName;
    }

    public void setOperUserName(String operUserName) {
        this.operUserName = operUserName;
    }

    public Integer getOperUser() {
        return operUser;
    }

    public void setOperUser(Integer operUser) {
        this.operUser = operUser;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getHandleUserName() {
        return handleUserName;
    }

    public void setHandleUserName(String handleUserName) {
        this.handleUserName = handleUserName;
    }

    public Integer getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(Integer handleUser) {
        this.handleUser = handleUser;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Integer getObj() {
        return obj;
    }

    public void setObj(Integer obj) {
        this.obj = obj;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
}
