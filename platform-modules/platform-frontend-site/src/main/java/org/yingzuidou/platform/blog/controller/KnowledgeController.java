package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping("/search/recommend")
    public CmsMap<List<KnowledgeDTO>> searchRecommendKnowledge() {
        List<KnowledgeDTO> knowledgeDTO = knowledgeService.listRecommend();
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTO);
    }

    @GetMapping("/search/recent")
    public CmsMap<List<KnowledgeDTO>> recentKnowledge() {
        List<KnowledgeDTO> knowledgeDTOS = knowledgeService.listRecent();
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOS);
    }

    /**
     * 根据分类分页查找知识库
     *
     * @param categoryId 分类ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    @GetMapping(value="/search/list")
    public CmsMap<List<KnowledgeDTO>> list(Integer categoryId, PageInfo pageInfo) {
        List<KnowledgeDTO> knowledgeDTOS = knowledgeService.listByCategory(categoryId, pageInfo);
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOS);
    }

    /**
     * 知识库详情
     *
     * @param knowledgeId 知识库ID
     * @return 知识库详情内容
     */
    @GetMapping(value = "/search/detail")
    public CmsMap<KnowledgeDTO> knowledgeDetail(Integer knowledgeId) {
        KnowledgeDTO knowledgeDTOS = knowledgeService.retrieveKnowledgeDetail(knowledgeId);
        return CmsMap.<KnowledgeDTO>ok().setResult(knowledgeDTOS);
    }

    /**
     * 用户创建的知识库
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    @GetMapping(value = "/search/user/create")
    public CmsMap<List<KnowledgeDTO>> retrieveUserKnowledge(Integer userId, PageInfo pageInfo) {
        List<KnowledgeDTO> knowledgeDTOList =knowledgeService.retrieveUserKnowledge(userId, pageInfo);
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOList);
    }

    /**
     * 用户参与的知识库列表
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    @GetMapping(value = "/search/user/participant")
    public CmsMap<List<KnowledgeDTO>> retrieveUserParticipant(Integer userId, PageInfo pageInfo) {
        List<KnowledgeDTO> knowledgeDTOList =knowledgeService.retrieveUserParticipant(userId, pageInfo);
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOList);
    }

}
