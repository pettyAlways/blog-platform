package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.service.ArticleService;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.vo.CmsMap;

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
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 所有知识库的最近八篇文航
     *
     * @return
     */
    @GetMapping("/search/recent")
    public CmsMap<List<ArticleDTO>> recentArticle() {
        List<ArticleDTO> articleDTOS = articleService.retrieveRecentArticle();
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取知识库下的文章目录
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库密码访问生成的token
     * @param userId 访问用户
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @GetMapping("/search/catalogue")
    public CmsMap<List<ArticleDTO>> catalogueArticleInKnowledge (
            Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveKnowledgeCatalogue(knowledgeId, token, userId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    @GetMapping("/search/recent/info")
    public CmsMap<List<ArticleDTO>> recentArticleListInfo() {
        List<ArticleDTO> articleDTOS = articleService.recentArticleListInfo();
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取知识库下最新发布文章
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库密码访问生成的token
     * @param userId 访问用户
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @GetMapping("/search/recent/post")
    public CmsMap<List<ArticleDTO>> recentPostArticleInKnowledge (
            Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveRecentPostArticleList(knowledgeId, token, userId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取知识库下最新编辑文章
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @GetMapping("/search/recent/edit")
    public CmsMap<List<ArticleDTO>> recentEditArticleInKnowledge (
            Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveRecentEditArticleList(knowledgeId, token, userId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 用户知识库简洁的文章列表
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户
     * @param pageInfo 分页信息
     * @return 简洁的文章列表
     */
    @GetMapping("/search/concise")
    public CmsMap<List<ArticleDTO>> conciseArticle (Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveConciseArticleList(knowledgeId, token, userId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取文章的信息
     *
     * @param articleId 文章ID
     * @param token 加密知识库访问的token
     * @param userId 访问用户
     * @return 文章信息
     */
    @GetMapping("/search/item")
    public CmsMap<ArticleDTO> retrieveArticle (Integer articleId, String token, Integer userId) {
        ArticleDTO articleDTO = articleService.retrieveArticle(articleId, token, userId);
        Map<String, ArticleDTO> preAndNextList = articleService.preAndNext(articleId);
        return CmsMap.<ArticleDTO>ok().appendData("preAndNext", preAndNextList).setResult(articleDTO);
    }

    @GetMapping("/search/recent/in-knowledge")
    public CmsMap<List<ArticleDTO>> recentArticleInKnowledge (Integer knowledgeId) {
        List<ArticleDTO> articleDTOS = articleService.recentArticleInKnowledge(knowledgeId);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取用户最近发布的文章
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 文章列表
     */
    @GetMapping("/search/user/post")
    public CmsMap<List<ArticleDTO>> retrieveUserRecentPost(Integer userId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOList = articleService.retrieveUserRecentPost(userId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOList);
    }

}
