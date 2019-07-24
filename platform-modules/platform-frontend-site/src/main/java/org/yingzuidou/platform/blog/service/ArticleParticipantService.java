package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.common.entity.CmsUserEntity;

import java.util.List;

public interface ArticleParticipantService {


    /**
     * 给文章增加参与者，拥有共享编辑权限的人可以修改其他人的文章，需要记录修改文章的参与者
     *
     * @param articleId 文章ID
     * @param user 参与者ID
     */
    void addArticleParticipant(int articleId, CmsUserEntity user);

    /**
     * 根据文章获取所有参与者
     *
     * @param articleId 文章ID
     * @return 文章下的所有参与者
     */
    List<CmsUserEntity> listParticipantByArticleId(int articleId);

    /**
     * 移除文章所关联的所有参与者
     *
     * @param articleId 文章ID
     */
    void removeAllArticleParticipantInArticle(int articleId);
}
