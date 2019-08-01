package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.blog.dao.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.*;
import org.yingzuidou.platform.common.constant.AccessEnum;
import org.yingzuidou.platform.common.constant.IsDeleteEnum;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.HtmlUtils;
import org.yingzuidou.platform.common.utils.PasswordJwtUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    /**
     * 返回所有用户最近发布的8篇文章
     *
     * @return 最近8篇文章
     */
    @Override
    public List<ArticleDTO> retrieveRecentArticle() {
        List<Object[]> data = articleRepository.recentArticle();
        return Optional.ofNullable(data).orElse(new ArrayList<>()).stream().map(item -> ArticleDTO.recentArticle
                .apply(item)).collect(Collectors.toList());
    }

    /**
     * 获取知识库下的文章目录列表
     *
     * @param knowledgeId 知识库Id
     * @param pageInfo 分页信息
     * @return 文章目录列表
     */
    @Override
    public List<ArticleDTO> retrieveKnowledgeCatalogue(
            Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!PasswordJwtUtil.verifyKnowledgeEncrypt(knowledgeEntity, userId, token)) {
            return null;
        }
        List<Object[]> articleEntityList = articleRepository.findArticleListInKnowledge(knowledgeId,
                IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable(Sort.Direction.ASC, "post_time"));
        if (!articleEntityList.isEmpty()) {
            return articleEntityList.stream().map(item -> ArticleDTO.articleList.apply(item)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 知识库下最近发布的文章列表
     *
     * @param knowledgeId 知识库ID
     * @param pageInfo 分页信息
     * @param token 知识库加密访问生成的token
     * @param userId 访问用户
     * @return 最近发布文章列表
     */
    @Override
    public List<ArticleDTO> retrieveRecentPostArticleList(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!PasswordJwtUtil.verifyKnowledgeEncrypt(knowledgeEntity, userId, token)) {
            return null;
        }
        List<Object[]> articleEntityList = articleRepository.findArticleListInKnowledge(knowledgeId,
                IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable(Sort.Direction.DESC, "post_time"));
        if (!articleEntityList.isEmpty()) {
            return articleEntityList.stream().map(item -> ArticleDTO.articleList.apply(item)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 知识库下最近编辑文章
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库加密访问生成的token
     * @param userId 访问用户ID
     * @param pageInfo 分页信息
     * @return 最近编辑的文章
     */
    @Override
    public List<ArticleDTO> retrieveRecentEditArticleList(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!PasswordJwtUtil.verifyKnowledgeEncrypt(knowledgeEntity, userId, token)) {
            return null;
        }
        List<Object[]> articleEntityList = articleRepository.findArticleListInKnowledge(knowledgeId,
                IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable(Sort.Direction.DESC, "update_time"));
        if (!articleEntityList.isEmpty()) {
            return articleEntityList.stream().map(item -> ArticleDTO.articleList.apply(item)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 文章列表只包含文章标题发布时间
     *
     * @param knowledgeId 知识库ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户ID
     * @param pageInfo 分页信息
     * @return 文章列表
     */
    @Override
    public List<ArticleDTO> retrieveConciseArticleList(Integer knowledgeId, String token, Integer userId, PageInfo pageInfo) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!PasswordJwtUtil.verifyKnowledgeEncrypt(knowledgeEntity, userId, token)) {
            return null;
        }
        List<ArticleEntity> articleEntityList = articleRepository
                .findAllByKnowledgeIdAndIsDelete(knowledgeId, IsDeleteEnum.NOTDELETE.getValue(),
                        pageInfo.toPageable(Sort.Direction.ASC, "postTime"));
        return Optional.ofNullable(articleEntityList).orElse(new ArrayList<>()).stream()
                .map(item -> new ArticleDTO().setArticleId(item.getId()).setArticleTitle(item.getArticleTitle())
                .setPostTime(item.getPostTime())).collect(Collectors.toList());
    }

    /**
     * 获取文章信息
     *
     * @param articleId 文章ID
     * @param token 知识库加密生成的token
     * @param userId 访问用户
     * @return 文章信息
     */
    @Override
    public ArticleDTO retrieveArticle(Integer articleId, String token, Integer userId) {
        Optional<ArticleEntity> articleEntityOp = articleRepository.findById(articleId);
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("文章不存在");
        }
        ArticleEntity articleEntity = articleEntityOp.get();
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(articleEntity.getKnowledgeId());
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!PasswordJwtUtil.verifyKnowledgeEncrypt(knowledgeEntity, userId, token)) {
            return null;
        }
        List<Object[]> data = articleRepository.findArticleShowInfo(articleId, IsDeleteEnum.NOTDELETE.getValue());
        if (data.isEmpty()) {
            throw new BusinessException("所查询的文章不存在");
        }

        ArticleDTO articleDTO = ArticleDTO.articleShow.apply(data.get(0));
        List<Object[]> participantList = articleRepository.findParticipantInArticle(articleId);
        List<UserDTO> userDTOList = Optional.ofNullable(participantList).orElse(new ArrayList<>()).stream()
                .map(item -> ArticleDTO.participantShow.apply(item))
                .collect(Collectors.toList());
        articleDTO.setParticipantList(userDTOList);

        return articleDTO;
    }

    @Override
    public List<ArticleDTO> recentArticleInKnowledge(Integer knowledgeId) {
        List<ArticleEntity> articleEntityList = articleRepository
                .findFirst6ByKnowledgeIdAndIsDeleteOrderByPostTimeDesc(knowledgeId, IsDeleteEnum.NOTDELETE.getValue());
        return Optional.ofNullable(articleEntityList).orElse(new ArrayList<>()).stream().map(item -> new ArticleDTO()
        .setArticleId(item.getId()).setArticleTitle(item.getArticleTitle())).collect(Collectors.toList());
    }

    /**
     * 获取用户最近发布的文章
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 文章列表
     */
    @Override
    public List<ArticleDTO> retrieveUserRecentPost(Integer userId, PageInfo pageInfo) {
        List<Object[]> articleEntityList = articleRepository.findArticleUserRecentPost(userId,
                 pageInfo.toPageable(Sort.Direction.DESC, "post_time"));
        return Optional.ofNullable(articleEntityList).orElse(new ArrayList<>()).stream().map(item -> ArticleDTO
                .recentPostList.apply(item)).collect(Collectors.toList());
    }
}
