package org.yingzuidou.platform.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
@NamedEntityGraph(name = "Knowledge.Graph", attributeNodes = {
        @NamedAttributeNode("kParticipant"), @NamedAttributeNode("kType"), @NamedAttributeNode("creator") })
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
    private String kDesc;

    @Basic
    @Column(name = "k_url")
    private String kUrl;

    @JoinTable(name="participant",
            joinColumns={@JoinColumn(name="knowledge_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="participant_id",referencedColumnName="id")}
    )
    @ManyToMany
    private List<CmsUserEntity> kParticipant;

    @Basic
    @Column(name = "k_access", insertable = false)
    private String kAccess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "k_type")
    @NotFound(action = NotFoundAction.IGNORE)
    private CategoryEntity kType;

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
    @Column(name = "is_delete", insertable = false)
    private String isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    @NotFound(action = NotFoundAction.IGNORE)
    private CmsUserEntity creator;

}
