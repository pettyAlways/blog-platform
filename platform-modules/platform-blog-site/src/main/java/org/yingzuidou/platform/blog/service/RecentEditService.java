package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.RecentEditEntity;

import java.util.List;

public interface RecentEditService {

    void saveRecentEditRecord(Integer userId, ArticleEntity article, KnowledgeEntity knowledge);

    void removeRecentArticleRecord(Integer articleId);

    List<RecentEditEntity> listRecentArticle(int id);

    /**
     * 获取指定用户最近编辑的知识库数量
     *
     * @param userId 用户ID
     * @param num 获取的知识库个数
     * @return 最近用户编辑的知识库列表
     */
    List<RecentKnowledgeDTO> listRecentKnowledge(int userId, Integer num);
}
