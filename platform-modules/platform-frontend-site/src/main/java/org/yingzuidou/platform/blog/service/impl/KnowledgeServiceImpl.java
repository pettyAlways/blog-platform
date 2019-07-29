package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.blog.dao.ArticleRepository;
import org.yingzuidou.platform.blog.dao.KnowledgeRepository;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.blog.service.ParticipantService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.constant.*;
import org.yingzuidou.platform.common.entity.*;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class KnowledgeServiceImpl implements KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private UserService userService;



    /**
     * 获取推荐的知识库列表,暂时获取文章最多的前3个知识库，返回每个知识库下5篇文章
     *
     * @return 推荐知识库列表
     */
    @Override
    public List<KnowledgeDTO> listRecommend() {
        List<KnowledgeDTO> knowledgeDTOList = new ArrayList<>();
        List<Object[]> dataList = articleRepository.findMostArticleKnowledgeLimit3();
        if (!dataList.isEmpty()) {
            Map<Integer, List<ArticleDTO>> articleDTOS = dataList.stream()
                    .map(data -> ArticleDTO.recommendKnowledge.apply(data))
                    .collect(Collectors.groupingBy(ArticleDTO::getKnowledgeId));
            for (Map.Entry<Integer, List<ArticleDTO>> entry : articleDTOS.entrySet()) {
                KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
                List<ArticleDTO> articleDTOList = entry.getValue();
                knowledgeDTO.setArticleList(articleDTOList);
                if (!articleDTOList.isEmpty()) {
                    ArticleDTO articleDTO = articleDTOList.get(0);
                    knowledgeDTO.setKnowledgeId(articleDTO.getKnowledgeId())
                            .setKnowledgeName(articleDTO.getKnowledgeName())
                            .setKnowledgeDesc(CmsBeanUtils.limitContent(articleDTO.getKnowledgeDesc(), 80))
                            .setKnowledgeCover(articleDTO.getKnowledgeCoverUrl());
                }
                knowledgeDTOList.add(knowledgeDTO);
            }
        }
        return knowledgeDTOList;
    }

    /**
     * 最近创建的知识库
     *
     * @return 最近创建的10个知识库
     */
    @Override
    public List<KnowledgeDTO> listRecent() {
        List<KnowledgeEntity> knowledgeEntities = knowledgeRepository
                .findFirst8ByIsDeleteOrderByEditTimeDesc(IsDeleteEnum.NOTDELETE.getValue());
        return Optional.ofNullable(knowledgeEntities).orElse(new ArrayList<>()).stream()
                .map(item -> new KnowledgeDTO().setKnowledgeId(item.getId()).setKnowledgeName(item.getKName())
                .setEditTime(item.getEditTime())).collect(Collectors.toList());
    }

    /**
     * 分页查找分类下的知识库
     *
     * @param categoryId 分类ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    @Override
    public List<KnowledgeDTO> listByCategory(Integer categoryId, PageInfo pageInfo) {
        List<KnowledgeEntity> knowledgeEntities = knowledgeRepository
                .findAllByKTypeAndIsDelete(categoryId, IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable());
        return Optional.ofNullable(knowledgeEntities).orElse(new ArrayList<>()).stream()
                .map(item -> new KnowledgeDTO().setKnowledgeId(item.getId()).setKnowledgeName(item.getKName())
                .setKnowledgeDesc(CmsBeanUtils.limitContent(item.getKDesc(), 30))
                .setKnowledgeCover(item.getKUrl()).setAccess(item.getKAccess()))
                .collect(Collectors.toList());
    }

    /**
     * 获取知识库详情内容
     *
     * @param knowledgeId 知识库ID
     * @return 知识库详情
     */
    @Override
    public KnowledgeDTO retrieveKnowledgeDetail(Integer knowledgeId) {
        KnowledgeDTO knowledgeDTO;
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        List<Object[]> data = knowledgeRepository.findKnowledgeDetail(knowledgeId, IsDeleteEnum.NOTDELETE.getValue());
        if (!data.isEmpty()) {
            knowledgeDTO = KnowledgeDTO.knowledgeDetail.apply(data.get(0));
            knowledgeDTO.setParticipantList(participantService.findAllParticipantInKnowledge(knowledgeId));
            ArticleEntity newPost = articleRepository
                    .findFirstByKnowledgeIdAndIsDeleteOrderByPostTime(knowledgeId, IsDeleteEnum.NOTDELETE.getValue());
            if (Objects.nonNull(newPost)) {
                knowledgeDTO.setRecentlyArticle(new ArticleDTO().setArticleId(newPost.getId())
                .setArticleTitle(newPost.getArticleTitle()).setKnowledgeId(newPost.getKnowledgeId()));
            }
            knowledgeDTO.setArticleCounts(articleRepository
                    .countByKnowledgeIdAndIsDelete(knowledgeId, IsDeleteEnum.NOTDELETE.getValue()));
            return knowledgeDTO;
        }
        return null;
    }

    /**
     * 用户创建的知识库
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    @Override
    public List<KnowledgeDTO> retrieveUserKnowledge(Integer userId, PageInfo pageInfo) {
        List<Object[]> knowledgeList = knowledgeRepository.findUserKnowledgeList(userId,
                pageInfo.toPageable(Sort.Direction.DESC, "createTime"));
        return Optional.ofNullable(knowledgeList).orElse(new ArrayList<>()).stream().parallel()
                .map(item -> KnowledgeDTO.userKnowledgeList.apply(item)).collect(Collectors.toList());
    }

    @Override
    public List<KnowledgeDTO> retrieveUserParticipant(Integer userId, PageInfo pageInfo) {
        List<Object[]> knowledgeList = knowledgeRepository.findUserParticipantKnowledgeList(userId,
                pageInfo.toPageable(Sort.Direction.DESC, "createTime"));
        return Optional.ofNullable(knowledgeList).orElse(new ArrayList<>()).stream().parallel()
                .map(item -> KnowledgeDTO.userParticipantList.apply(item)).collect(Collectors.toList());
    }

    /**
     * 获取知识库创建者信息
     *
     * @param knowledgeId 知识库ID
     * @return 知识库创建者信息
     */
    @Override
    public UserDTO retrieveKnowledgeCreator(Integer knowledgeId) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        return userService.retrieveUserProfile(knowledgeEntity.getCreator());
    }

    /**
     * 获取知识库下的协作人资料
     *
     * @param knowledgeId 知识库ID
     * @return 协作人资料
     */
    @Override
    public List<UserDTO> retrieveKnowledgeParticipant(Integer knowledgeId) {
        List<UserDTO> userDTOList = participantService.findAllParticipantInKnowledge(knowledgeId);
        return Optional.ofNullable(userDTOList).orElse(new ArrayList<>()).stream()
                .map(item -> userService.retrieveUserProfile(item.getUserId())).collect(Collectors.toList());
    }
}
