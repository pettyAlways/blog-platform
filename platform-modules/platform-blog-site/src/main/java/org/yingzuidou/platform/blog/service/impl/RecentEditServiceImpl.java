package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.RecentEditRepository;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.blog.service.RecentEditService;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.RecentEditEntity;

import java.util.Date;
import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/14
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class RecentEditServiceImpl implements RecentEditService {

    @Autowired
    private RecentEditRepository recentEditRepository;

    @Override
    public void saveRecentEditRecord(Integer userId, ArticleEntity article, KnowledgeEntity knowledge) {
        RecentEditEntity recentEditEntity = new RecentEditEntity();
        recentEditEntity.setUserId(userId);
        recentEditEntity.setArticle(article);
        recentEditEntity.setKnowledge(knowledge);
        recentEditEntity.setEditTime(new Date());
        recentEditRepository.save(recentEditEntity);
    }

    @Override
    public void removeRecentArticleRecord(Integer articleId) {
        recentEditRepository.removeAllByArticleId(articleId);
    }

    @Override
    public List<RecentEditEntity> listRecentArticle(int id) {
        return recentEditRepository.findFirst6ByUserIdOrderByEditTimeDesc(id);
    }

    /**
     * 获取指定用户最近编辑的知识库数量
     *
     * @param userId 用户ID
     * @param num 知识库数量
     * @return 最近编辑的知识库列表
     */
    @Override
    public List<RecentKnowledgeDTO> listRecentKnowledge(int userId, Integer num) {
        return recentEditRepository.findRecentKnowledge(userId, num);
    }
}
