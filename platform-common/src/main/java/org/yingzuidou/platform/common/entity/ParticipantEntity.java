package org.yingzuidou.platform.common.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/8
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Entity
@Table(name = "participant", schema = "cms_web", catalog = "")
@NamedEntityGraph(name = "Participant.Graph", attributeNodes = {
        @NamedAttributeNode("knowledge"), @NamedAttributeNode("participant")})
public class ParticipantEntity {
    private int id;
    private KnowledgeEntity knowledge;
    private CmsUserEntity participant;
    private int creator;
    private Date createTime;
    private Integer updator;
    private Date updateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    public KnowledgeEntity getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(KnowledgeEntity knowledge) {
        this.knowledge = knowledge;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    public CmsUserEntity getParticipant() {
        return participant;
    }

    public void setParticipant(CmsUserEntity participant) {
        this.participant = participant;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantEntity that = (ParticipantEntity) o;
        return id == that.id &&
                creator == that.creator &&
                Objects.equals(knowledge, that.knowledge) &&
                Objects.equals(participant, that.participant) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updator, that.updator) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, knowledge, participant, creator, createTime, updator, updateTime);
    }
}
