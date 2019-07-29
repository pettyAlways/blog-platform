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
@Table(name = "audit", schema = "cms_web", catalog = "")
public class AuditEntity {

    @Id
    @Column(name = "id")
    private int id;

    /**
     * 申请人
     */
    @Basic
    @Column(name = "apply_user")
    private Integer applyUser;

    /**
     * 处理人
     */
    @Basic
    @Column(name = "handle_user")
    private Integer handleUser;

    /**
     * 申请类型：1.成为作者,2.加入知识库，
     */
    @Basic
    @Column(name = "apply_type")
    private String applyType;

    /**
     * 申请对象ID
     */
    @Basic
    @Column(name = "apply_obj")
    private Integer applyObj;

    /**
     * 处理结果：1.通过，2.不通过
     */
    @Basic
    @Column(name = "handle_result")
    private String handleResult;

    /**
     * 申请理由
     */
    @Basic
    @Column(name = "reason")
    private String reason;

    /**
     * 处理日期
     */
    @Basic
    @Column(name = "handle_time")
    private Date handleTime;

    /**
     * 处理日期
     */
    @Basic
    @Column(name = "apply_time")
    private Date applyTime;

}
