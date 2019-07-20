package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.yingzuidou.platform.common.validate.TelNum;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * CmsUserEntity
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "cms_user", schema = "my-cms", catalog = "")
public class CmsUserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "user_name")
    @Length(max = 30, message = "用户名不能超过30个字符")
    private String userName;

    @Basic
    @Column(name = "user_sex")
    private String userSex;

    @Basic
    @Column(name = "user_account")
    @Length(max = 30, message = "账号不能超过30个字符")
    private String userAccount;

    @Basic
    @Column(name = "user_password")
    @Length(max = 255, message = "密码长度不能超过255个字符")
    private String userPassword;

    @Basic
    @Column(name = "user_status")
    private String userStatus;

    @Basic
    @Column(name = "user_tel")
    @TelNum
    private String userTel;

    @Basic
    @Column(name = "user_mail")
    @Email
    private String userMail;

    @Basic
    @Column(name = "user_depart")
    private Integer userDepart;

    @Basic
    @Column(name = "creator")
    private int creator;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "updator")
    private Integer updator;

    @Basic
    @Column(name = "update_time")
    private Date updateTime;

    @Basic
    @Column(name = "is_delete")
    private String isDelete;

    @Basic
    @Column(name = "uuid")
    private String uuid;

    @Basic
    @Column(name = "login_time")
    private Date loginTime;

    @Basic
    @Column(name = "lock_time")
    private Date lockTime;

}
