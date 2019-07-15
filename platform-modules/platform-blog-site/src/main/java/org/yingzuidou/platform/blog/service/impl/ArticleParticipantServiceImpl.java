package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.ArticleParticipantRepository;
import org.yingzuidou.platform.blog.service.ArticleParticipantService;
import org.yingzuidou.platform.common.entity.ArticleParticipantEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/15
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class ArticleParticipantServiceImpl implements ArticleParticipantService {

    @Autowired
    private ArticleParticipantRepository articleParticipantRepository;

    /**
     * 给文章增加参与者，拥有共享编辑权限的人可以修改其他人的文章，需要记录修改文章的参与者
     *
     * @param articleId 文章ID
     * @param user 参与者
     */
    @Override
    public void addArticleParticipant(int articleId, CmsUserEntity user) {
        // 如果已经存在该参与者这不需要保存
        if (articleParticipantRepository.existsByArticleIdAndUserId(articleId, user.getId())) {
            return;
        }
        ArticleParticipantEntity articleParticipantEntity = new ArticleParticipantEntity();
        articleParticipantEntity.setArticleId(articleId);
        articleParticipantEntity.setUser(user);
        articleParticipantRepository.save(articleParticipantEntity);
    }

    /**
     * 根据文章获取所有参与者
     *
     * @param articleId 文章ID
     * @return 文章下的所有参与者
     */
    @Override
    public List<CmsUserEntity> listParticipantByArticleId(int articleId) {
        List<ArticleParticipantEntity> participantList = articleParticipantRepository.findAllByArticleId(articleId);
        return Optional.ofNullable(participantList).orElse(new ArrayList<>()).stream()
                .map(ArticleParticipantEntity::getUser).collect(Collectors.toList());
    }

    /**
     * 移除文章所关联的所有参与者
     *
     * @param articleId 文章ID
     */
    @Override
    public void removeAllArticleParticipantInArticle(int articleId) {
        articleParticipantRepository.removeAllByArticleId(articleId);
    }
}
