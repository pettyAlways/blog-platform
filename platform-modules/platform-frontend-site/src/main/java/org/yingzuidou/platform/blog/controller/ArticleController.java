package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.service.ArticleService;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.vo.CmsMap;

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
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @GetMapping("/search/catalogue")
    public CmsMap<List<ArticleDTO>> catalogueArticleInKnowledge (Integer knowledgeId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveKnowledgeCatalogue(knowledgeId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取知识库下最新发布文章
     *
     * @param knowledgeId 知识库ID
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @GetMapping("/search/recent/post")
    public CmsMap<List<ArticleDTO>> recentPostArticleInKnowledge (Integer knowledgeId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveRecentPostArticleList(knowledgeId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    /**
     * 获取知识库下最新编辑文章
     *
     * @param knowledgeId 知识库ID
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @GetMapping("/search/recent/edit")
    public CmsMap<List<ArticleDTO>> recentEditArticleInKnowledge (Integer knowledgeId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveRecentEditArticleList(knowledgeId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    @GetMapping("/search/concise")
    public CmsMap<List<ArticleDTO>> conciseArticle (Integer knowledgeId, PageInfo pageInfo) {
        List<ArticleDTO> articleDTOS = articleService.retrieveConciseArticleList(knowledgeId, pageInfo);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTOS);
    }

    @GetMapping("/search/item")
    public CmsMap<ArticleDTO> retrieveArticle (Integer articleId) {
        ArticleDTO articleDTO = articleService.retrieveArticle(articleId);
        return CmsMap.<ArticleDTO>ok().setResult(articleDTO);
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
