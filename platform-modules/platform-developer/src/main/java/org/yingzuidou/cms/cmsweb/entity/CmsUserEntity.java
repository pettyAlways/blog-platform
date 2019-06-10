package org.yingzuidou.cms.cmsweb.entity;

import org.hibernate.validator.constraints.Length;
import org.yingzuidou.cms.cmsweb.core.validate.TelNum;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Objects;

/**
 * CmsUserEntity
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Entity
@Table(name = "cms_user", schema = "my-cms", catalog = "")
public class CmsUserEntity {
    private int id;

    @Length(max = 30, message = "用户名不能超过30个字符")
    private String userName;
    private String userSex;
    @Length(max = 30, message = "账号不能超过30个字符")
    private String userAccount;
    @Length(max = 255, message = "密码长度不能超过255个字符")
    private String userPassword;
    private String userStatus;
    @TelNum
    private String userTel;
    @Email
    private String userMail;
    private Integer userDepart;
    private int creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;
    private String isDelete;
    private String uuid;
    private Date loginTime;
    private Date lockTime;

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
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_sex")
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Basic
    @Column(name = "user_account")
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Basic
    @Column(name = "user_password")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "user_status")
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Basic
    @Column(name = "user_tel")
    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    @Basic
    @Column(name = "user_mail")
    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Basic
    @Column(name = "user_depart")
    public Integer getUserDepart() {
        return userDepart;
    }

    public void setUserDepart(Integer userDepart) {
        this.userDepart = userDepart;
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
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "login_time")
    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "lock_time")
    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CmsUserEntity that = (CmsUserEntity) o;
        return id == that.id &&
                creator == that.creator &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userSex, that.userSex) &&
                Objects.equals(userAccount, that.userAccount) &&
                Objects.equals(userPassword, that.userPassword) &&
                Objects.equals(userStatus, that.userStatus) &&
                Objects.equals(userTel, that.userTel) &&
                Objects.equals(userMail, that.userMail) &&
                Objects.equals(userDepart, that.userDepart) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updator, that.updator) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, userSex, userAccount, userPassword, userStatus, userTel, userMail, userDepart, creator, createTime, updator, updateTime, isDelete);
    }
}
