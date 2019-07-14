package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.common.entity.ArticleEntity;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ArticleService {

    /**
     * 根据知识库查找所有文章的标题以及作者的信息
     *
     * @param knowledgeId 知识库
     * @return 知识库下的文章目录
     */
    List<ArticleDTO> findArticleByKnowledge(Integer knowledgeId);

    ArticleEntity addArticle(ArticleEntity articleEntity);

    ArticleDTO getArticle(Integer articleId);

    ArticleEntity editArticle(ArticleEntity articleEntity);

    ArticleEntity editShareArticle(ArticleEntity articleEntity);

    ArticleDTO deleteArticle(Integer articleId);

    ArticleDTO deleteShareArticle(Integer articleId);
}
