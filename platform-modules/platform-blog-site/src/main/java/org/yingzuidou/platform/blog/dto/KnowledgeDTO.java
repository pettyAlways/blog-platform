package org.yingzuidou.platform.blog.dto;

import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.ParticipantEntity;

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
public class KnowledgeDTO extends KnowledgeEntity {


    private String showWay;

    private String createName;

    private List<ArticleEntity> articleEntities;

    private List<ParticipantEntity> participantEntities;


    public String getShowWay() {
        return showWay;
    }

    public void setShowWay(String showWay) {
        this.showWay = showWay;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public List<ArticleEntity> getArticleEntities() {
        return articleEntities;
    }

    public void setArticleEntities(List<ArticleEntity> articleEntities) {
        this.articleEntities = articleEntities;
    }

    public List<ParticipantEntity> getParticipantEntities() {
        return participantEntities;
    }

    public void setParticipantEntities(List<ParticipantEntity> participantEntities) {
        this.participantEntities = participantEntities;
    }
}
