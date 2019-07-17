package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.ParticipantEntity;

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
public class KnowledgeDTO extends KnowledgeEntity {


    private String showWay;

    private String createName;

    private List<ArticleEntity> articleEntities;

    private List<ParticipantEntity> participantEntities;

}
