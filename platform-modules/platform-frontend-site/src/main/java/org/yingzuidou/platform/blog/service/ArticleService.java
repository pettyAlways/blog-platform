package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.paging.PageInfo;

import java.util.List;
import java.util.Map;

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
     * 获取所有用户下最近发布的文章
     *
     * @return 最近8篇文章
     */
    List<ArticleDTO> retrieveRecentArticle();

    /**
     * 获取知识库下的文章目录列表
     *
     * @param knowledgeId 知识库Id
     * @param token 知识库密码访问生成的token
     * @param userId 访问用户
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    List<ArticleDTO> retrieveKnowledgeCatalogue(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo);

    /**
     * 知识库下最近发布的文章列表
     *
     * @param knowledgeId 知识库ID
     * @param pageInfo 分页信息
     * @param token 知识库加密访问生成的token
     * @param userId 访问用户
     * @return 最近发布文章列表
     */
    List<ArticleDTO> retrieveRecentPostArticleList(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo);

    /**
     * 知识库下最近编辑文章
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库加密访问生成的token
     * @param userId 访问用户ID
     * @param pageInfo 分页信息
     * @return 最近编辑的文章
     */
    List<ArticleDTO> retrieveRecentEditArticleList(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo);

    /**
     * 文章列表只包含文章标题发布时间
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户ID
     * @param pageInfo 分页信息
     * @return 文章列表
     */
    List<ArticleDTO> retrieveConciseArticleList(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo);

    /**
     * 获取文章信息
     *
     * @param articleId 文章ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户
     * @return 文章信息
     */
    ArticleDTO retrieveArticle(Integer articleId, String token, Integer userId);

    /**
     * 获取知识库下的最近发布的文章
     *
     * @param knowledgeId 知识库ID
     * @return 最近文章列表
     */
    List<ArticleDTO> recentArticleInKnowledge(Integer knowledgeId);

    /**
     * 获取用户最近发布的文章
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 用户列表
     */
    List<ArticleDTO> retrieveUserRecentPost(Integer userId, PageInfo pageInfo);

    /**
     * 上一篇和下一篇文章
     *
     * @param articleId 文章ID
     * @return 上一篇和下一篇列表
     */
    Map<String, ArticleDTO> preAndNext(Integer articleId);

    /**
     * 最近文章列表
     *
     * @return 最近文章列表
     */
    List<ArticleDTO> recentArticleListInfo();
}
