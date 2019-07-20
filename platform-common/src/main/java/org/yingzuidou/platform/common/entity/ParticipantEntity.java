package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/8
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "participant", schema = "cms_web", catalog = "")
public class ParticipantEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "knowledge_id")
    private Integer knowledgeId;

    @Basic
    @Column(name = "participant_id")
    private Integer participantId;

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

}
