package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * UserRoleEntity
 *
 * @author shangguanls
 * @date 2018/10/9
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "user_role", schema = "my-cms", catalog = "")
public class UserRoleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "role_id")
    private Integer roleId;

    @Basic
    @Column(name = "creator")
    private int creator;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

}
