package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "message", schema = "cms_web", catalog = "")
public class MessageEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "message")
    private String message;

    @Basic
    @Column(name = "m_read")
    private String mRead;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "reserve")
    private String reserve;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "m_type")
    private String mType;

}
