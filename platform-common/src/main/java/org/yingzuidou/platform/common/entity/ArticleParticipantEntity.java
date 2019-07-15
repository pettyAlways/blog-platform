package org.yingzuidou.platform.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/11
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Entity
@Table(name = "article_participant", schema = "cms_web", catalog = "")
@NamedEntityGraph(name = "ArticleParticipant.Graph", attributeNodes = {
        @NamedAttributeNode("user")
})
public class ArticleParticipantEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "article_id")
    private Integer articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private CmsUserEntity user;

}
