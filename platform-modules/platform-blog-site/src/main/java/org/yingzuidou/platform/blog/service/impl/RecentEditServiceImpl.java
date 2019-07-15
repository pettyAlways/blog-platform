package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.RecentEditRepository;
import org.yingzuidou.platform.blog.dto.RecentArticleDTO;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.blog.service.RecentEditService;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.RecentEditEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
@Transactional
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

    /**
     * 返回指定用户最近编辑的文章列表
     *
     * @return 返回6条该用户最近编辑的文章
     */
    @Override
    public List<RecentArticleDTO> listRecentArticle(Integer num) {
        List<RecentArticleDTO> recentArticleDTOList = new ArrayList<>();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<Object[]> recentEditEntities = recentEditRepository.findRecentArticle(user.getId(), num);
        if (!recentEditEntities.isEmpty()) {
            recentArticleDTOList = recentEditEntities.stream().map(item -> RecentArticleDTO.transform.apply(item))
                    .collect(Collectors.toList());
        }
        return recentArticleDTOList;
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
        List<Object[]> dataList = recentEditRepository.findRecentKnowledge(userId, num);
        return Optional.ofNullable(dataList).orElse(new ArrayList<>()).stream()
                .map(data -> RecentKnowledgeDTO.function.apply(data)).collect(Collectors.toList());
    }
}
