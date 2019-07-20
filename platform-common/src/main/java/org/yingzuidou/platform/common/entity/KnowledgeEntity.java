package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "knowledge", schema = "cms_web", catalog = "")
public class KnowledgeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "k_name")
    private String kName;

    @Basic
    @Column(name = "k_desc")
    @Length(max = 300, message = "账号不能超过300个字符")
    private String kDesc;

    @Basic
    @Column(name = "k_url")
    private String kUrl;

    @Basic
    @Column(name = "k_access", insertable = false)
    private String kAccess;

    @Basic
    @Column(name = "k_type")
    private Integer kType;

    @Basic
    @Column(name = "k_reserve_o")
    private String kReserveO;

    @Basic
    @Column(name = "k_reserve_t")
    private String kReserveT;

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
    @Column(name = "edit_time")
    private Date editTime;

    @Basic
    @Column(name = "is_delete", insertable = false)
    private String isDelete;

    @Basic
    @Column(name = "creator")
    private Integer creator;

}
