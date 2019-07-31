package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
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
     * 分类下其他公开访问的知识库
     *
     * @param categoryId 分类ID
     * @param pageInfo 分页信息
     * @return 分类下的其他公开访问的知识库
     */
    @GetMapping(value="/search/relate")
    public CmsMap<List<KnowledgeDTO>> listRelate(Integer categoryId, PageInfo pageInfo) {
        List<KnowledgeDTO> knowledgeDTOS = knowledgeService.listCouldAccessKnowledgeByCategory(categoryId, pageInfo);
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOS);
    }

    /**
     * 知识库详情
     *
     * @param knowledgeId 知识库ID
     * @param token 加密的知识库需要token访问
     * @param userId 访问用户
     * @return 知识库详情内容
     */
    @GetMapping(value = "/search/detail")
    public CmsMap<KnowledgeDTO> knowledgeDetail(Integer knowledgeId, String token, Integer userId) {
        KnowledgeDTO knowledgeDTOS = knowledgeService.retrieveKnowledgeDetail(knowledgeId, token, userId);
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
        List<KnowledgeDTO> knowledgeDTOList = knowledgeService.retrieveUserKnowledge(userId, pageInfo);
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
        List<KnowledgeDTO> knowledgeDTOList = knowledgeService.retrieveUserParticipant(userId, pageInfo);
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOList);
    }

    /**
     * 用户知识库创建人信息
     *
     * @param knowledgeId 用户ID
     * @return 创建人信息
     */
    @GetMapping(value = "/search/creator")
    public CmsMap<UserDTO> retrieveCreatorInfo(Integer knowledgeId) {
        UserDTO userDTO = knowledgeService.retrieveKnowledgeCreator(knowledgeId);
        return CmsMap.<UserDTO>ok().setResult(userDTO);
    }

    /**
     * 用户知识库协作人信息
     *
     * @param knowledgeId 用户ID
     * @return 协作人信息
     */
    @GetMapping(value = "/search/participant")
    public CmsMap<List<UserDTO>> retrieveParticipantInfo(Integer knowledgeId) {
        List<UserDTO> userDTOList = knowledgeService.retrieveKnowledgeParticipant(knowledgeId);
        return CmsMap.<List<UserDTO>>ok().setResult(userDTOList);
    }

    @PostMapping(value= "join")
    public CmsMap joinKnowledge(Integer knowledgeId, String reason) {
        knowledgeService.joinKnowledge(knowledgeId, reason);
        return CmsMap.ok();
    }

    @GetMapping(value= "/search/already-join")
    public CmsMap alreadyJoin(Integer knowledgeId) {
        boolean alreadyJoin = knowledgeService.alreadyJoinKnowledge(knowledgeId);
        return CmsMap.ok().appendData("alreadyJoin", alreadyJoin);
    }

    @GetMapping(value= "/password/verify")
    public CmsMap passwordVerify(Integer knowledgeId, String password) {
        String token = knowledgeService.passwordVerify(knowledgeId, password);
        return CmsMap.ok().appendData("accessToken", token);
    }
}
