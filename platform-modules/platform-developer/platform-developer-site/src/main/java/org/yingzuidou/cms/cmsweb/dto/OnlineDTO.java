package org.yingzuidou.cms.cmsweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2018/11/26
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class OnlineDTO {

    /**
     * 用户主键
     */
    private Integer userId;

    /**
     * 在线用户名
     */
    private String userName;

    /**
     * 在线用户账号
     */
    private String userAccount;

    /**
     * 所属角色
     */
    private String roleName;

    /**
     * 上次登录时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 所属部门
     */
    private String departName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
