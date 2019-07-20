package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

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
@Data
@Accessors(chain = true)
@Entity
@Table(name = "oper_record", schema = "cms_web", catalog = "")
public class OperRecordEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "oper_user")
    private Integer operUser;

    @Basic
    @Column(name = "oper_time")
    private Date operTime;

    @Basic
    @Column(name = "oper_type")
    private String operType;

    @Basic
    @Column(name = "handle_user")
    private Integer handleUser;

    @Basic
    @Column(name = "handle_result")
    private String handleResult;

    @Basic
    @Column(name = "obj_type")
    private String objType;

    @Basic
    @Column(name = "obj")
    private Integer obj;

    @Basic
    @Column(name = "root_type")
    private String rootType;

    @Basic
    @Column(name = "root_obj")
    private Integer rootObj;

    @Basic
    @Column(name = "reserve")
    private String reserve;

}
