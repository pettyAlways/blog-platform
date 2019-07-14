package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.dto.RecentAritcleDTO;
import org.yingzuidou.platform.blog.service.ArticleService;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.common.entity.ArticleEntity;
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

    @GetMapping("/list")
    public CmsMap<List<ArticleDTO>> articleDirInKnowledge(@RequestParam("knowledgeId") Integer knowledgeId) {
        List<ArticleDTO> articleDTO = articleService.findArticleByKnowledge(knowledgeId);
        return CmsMap.<List<ArticleDTO>>ok().setResult(articleDTO);
    }

    @PostMapping("/post")
    public CmsMap articleAdd(@RequestBody ArticleEntity articleEntity) {
        articleEntity = articleService.addArticle(articleEntity);
        return CmsMap.ok().appendData("articleId", articleEntity.getId())
                .appendData("knowledgeId", articleEntity.getKnowledge().getId());
    }

    /**
     * 只能修改自己的文章
     *
     * @param articleEntity 文章
     * @return 修改结果
     */
    @PutMapping("/edit")
    public CmsMap articleEdit(@RequestBody ArticleEntity articleEntity) {
        articleEntity = articleService.editArticle(articleEntity);
        return CmsMap.ok().appendData("articleId", articleEntity.getId())
                .appendData("knowledgeId", articleEntity.getKnowledge().getId());
    }

    /**
     * 共享修改其他文章
     *
     * @param articleEntity 文章内容
     * @return 修改结果
     */
    @PutMapping("/share/edit")
    public CmsMap articleShareEdit(@RequestBody ArticleEntity articleEntity) {
        articleEntity = articleService.editShareArticle(articleEntity);
        return CmsMap.ok().appendData("articleId", articleEntity.getId())
                .appendData("knowledgeId", articleEntity.getKnowledge().getId());
    }

    @GetMapping("/get")
    public CmsMap<ArticleDTO> getArticle(@RequestParam("articleId") Integer articleId) {
        ArticleDTO articleDTO = articleService.getArticle(articleId);
        return CmsMap.<ArticleDTO>ok().setResult(articleDTO);
    }

    @DeleteMapping("/delete")
    public CmsMap<ArticleDTO> deleteArticle(@RequestParam("articleId") Integer articleId) {
        ArticleDTO articleDTO = articleService.deleteArticle(articleId);
        return CmsMap.<ArticleDTO>ok().setResult(articleDTO);
    }

    @DeleteMapping("/share/delete")
    public CmsMap<ArticleDTO> deleteShareArticle(@RequestParam("articleId") Integer articleId) {
        ArticleDTO articleDTO = articleService.deleteShareArticle(articleId);
        return CmsMap.<ArticleDTO>ok().setResult(articleDTO);
    }
}
