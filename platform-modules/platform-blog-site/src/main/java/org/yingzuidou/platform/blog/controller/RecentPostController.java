package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dto.RecentArticleDTO;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.blog.dto.RecentPostDTO;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.blog.service.RecentEditService;
import org.yingzuidou.platform.blog.service.RecentPostService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/recent")
public class RecentPostController {

    @Autowired
    private RecentPostService recentPostService;

    @Autowired
    private OperRecordService operRecordService;

    @Autowired
    private RecentEditService recentEditService;

    @GetMapping("/post")
    public CmsMap<List<RecentPostDTO>> retrieveRecentPost() {
        List<RecentPostDTO> recentPostDTOList =  recentPostService.getRecentPost();
        return CmsMap.<List<RecentPostDTO>>ok().setResult(recentPostDTOList);
    }

    /**
     * 获取当前用户最近编辑的6篇文章
     *
     * @return 最近编辑的文章列表
     */
    @GetMapping("/article/edit")
    public CmsMap<List<RecentArticleDTO>> getRecentArticleList(@RequestParam("listNum") Integer num) {
        List<RecentArticleDTO> recentArticleDTOS = recentEditService.listRecentArticle(num);
        return CmsMap.<List<RecentArticleDTO>>ok().setResult(recentArticleDTOS);
    }

    /**
     * 获取用户最近编辑的知识库列表
     *
     * @param num 显示的知识库数量
     * @return 最近编辑的知识库列表
     */
    @GetMapping("/knowledge/edit")
    public CmsMap<List<RecentKnowledgeDTO>> getRecentKnowledgeList(@RequestParam("listNum") Integer num) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<RecentKnowledgeDTO> recentArticleDTOS = recentEditService.listRecentKnowledge(user.getId(), num);
        return CmsMap.<List<RecentKnowledgeDTO>>ok().setResult(recentArticleDTOS);
    }
}
