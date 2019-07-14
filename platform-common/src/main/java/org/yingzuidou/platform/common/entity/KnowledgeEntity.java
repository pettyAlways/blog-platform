package org.yingzuidou.platform.common.entity;

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
@Entity
@Table(name = "knowledge", schema = "cms_web", catalog = "")
@NamedEntityGraph(name = "Knowledge.Graph", attributeNodes = {
        @NamedAttributeNode("kParticipant"), @NamedAttributeNode("kType"), @NamedAttributeNode("creator") })
public class KnowledgeEntity {
    private int id;
    private String kName;
    private String kDesc;
    private String kUrl;
    private List<CmsUserEntity> kParticipant;
    private String kAccess;
    private CategoryEntity kType;
    private String kReserveO;
    private String kReserveT;
    private Date createTime;
    private Integer updator;
    private Date updateTime;
    private String isDelete;
    private CmsUserEntity creator;

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
    @Column(name = "k_name")
    public String getkName() {
        return kName;
    }

    public void setkName(String kName) {
        this.kName = kName;
    }

    @Basic
    @Column(name = "k_desc")
    public String getkDesc() {
        return kDesc;
    }

    public void setkDesc(String kDesc) {
        this.kDesc = kDesc;
    }

    @Basic
    @Column(name = "k_url")
    public String getkUrl() {
        return kUrl;
    }

    public void setkUrl(String kUrl) {
        this.kUrl = kUrl;
    }

    @JoinTable(name="participant",
            joinColumns={@JoinColumn(name="knowledge_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="participant_id",referencedColumnName="id")}
    )
    @ManyToMany
    public List<CmsUserEntity> getkParticipant() {
        return kParticipant;
    }

    public void setkParticipant(List<CmsUserEntity> kParticipant) {
        this.kParticipant = kParticipant;
    }

    @Basic
    @Column(name = "k_access", insertable = false)
    public String getkAccess() {
        return kAccess;
    }

    public void setkAccess(String kAccess) {
        this.kAccess = kAccess;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "k_type")
    @NotFound(action = NotFoundAction.IGNORE)
    public CategoryEntity getkType() {
        return kType;
    }

    public void setkType(CategoryEntity kType) {
        this.kType = kType;
    }

    @Basic
    @Column(name = "k_reserve_o")
    public String getkReserveO() {
        return kReserveO;
    }

    public void setkReserveO(String kReserveO) {
        this.kReserveO = kReserveO;
    }

    @Basic
    @Column(name = "k_reserve_t")
    public String getkReserveT() {
        return kReserveT;
    }

    public void setkReserveT(String kReserveT) {
        this.kReserveT = kReserveT;
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
    @Column(name = "is_delete", insertable = false)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    @NotFound(action = NotFoundAction.IGNORE)
    public CmsUserEntity getCreator() {
        return creator;
    }

    public void setCreator(CmsUserEntity cmsUserEntity) {
        this.creator = cmsUserEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KnowledgeEntity that = (KnowledgeEntity) o;
        return id == that.id &&
                Objects.equals(kName, that.kName) &&
                Objects.equals(kDesc, that.kDesc) &&
                Objects.equals(kUrl, that.kUrl) &&
                Objects.equals(kParticipant, that.kParticipant) &&
                Objects.equals(kAccess, that.kAccess) &&
                Objects.equals(kType, that.kType) &&
                Objects.equals(kReserveO, that.kReserveO) &&
                Objects.equals(kReserveT, that.kReserveT) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updator, that.updator) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDelete, that.isDelete) &&
                Objects.equals(creator, that.creator);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, kName, kDesc, kUrl, kParticipant, kAccess, kType, kReserveO, kReserveT, createTime, updator, updateTime, isDelete, creator);
    }
}
