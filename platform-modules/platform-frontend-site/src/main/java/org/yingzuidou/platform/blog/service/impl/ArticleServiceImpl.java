package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.blog.dao.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.*;
import org.yingzuidou.platform.common.constant.IsDeleteEnum;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

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
    public List<ArticleDTO> retrieveKnowledgeCatalogue(Integer knowledgeId, PageInfo pageInfo) {
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
     * @return 最近发布文章列表
     */
    @Override
    public List<ArticleDTO> retrieveRecentPostArticleList(Integer knowledgeId, PageInfo pageInfo) {
        List<Object[]> articleEntityList = articleRepository.findArticleListInKnowledge(knowledgeId,
                IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable(Sort.Direction.DESC, "post_time"));
        if (!articleEntityList.isEmpty()) {
            return articleEntityList.stream().map(item -> ArticleDTO.articleList.apply(item)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 知识库下最近编辑的文章列表
     *
     * @param knowledgeId 知识库ID
     * @param pageInfo 分页信息
     * @return 最近编辑文章列表
     */
    @Override
    public List<ArticleDTO> retrieveRecentEditArticleList(Integer knowledgeId, PageInfo pageInfo) {
        List<Object[]> articleEntityList = articleRepository.findArticleListInKnowledge(knowledgeId,
                IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable(Sort.Direction.DESC, "update_time"));
        if (!articleEntityList.isEmpty()) {
            return articleEntityList.stream().map(item -> ArticleDTO.articleList.apply(item)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 获取文章精简列表，只显示文章标题和发布时间
     *
     * @param knowledgeId 知识库ID
     * @param pageInfo 分页信息
     * @return 文章列表
     */
    @Override
    public List<ArticleDTO> retrieveConciseArticleList(Integer knowledgeId, PageInfo pageInfo) {
        List<ArticleEntity> articleEntityList = articleRepository
                .findAllByKnowledgeIdAndIsDelete(knowledgeId, IsDeleteEnum.NOTDELETE.getValue(),
                        pageInfo.toPageable(Sort.Direction.ASC, "postTime"));
        return Optional.ofNullable(articleEntityList).orElse(new ArrayList<>()).stream()
                .map(item -> new ArticleDTO().setArticleId(item.getId()).setArticleTitle(item.getArticleTitle())
                .setPostTime(item.getPostTime())).collect(Collectors.toList());
    }

    /**
     * 获取文章信息内容
     *
     * @param articleId 文章ID
     * @return 文章信息
     */
    @Override
    public ArticleDTO retrieveArticle(Integer articleId) {
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
        List<ArticleEntity> articleEntityList = articleRepository.findAllByAuthorIdAndIsDelete(userId,
                IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable(Sort.Direction.DESC, "postTime"));
        return Optional.ofNullable(articleEntityList).orElse(new ArrayList<>()).stream().map(item -> new ArticleDTO()
        .setArticleId(item.getId()).setArticleTitle(item.getArticleTitle())
                .setContent(CmsBeanUtils.limitContent(item.getContent(), 100)).setPostTime(item.getPostTime())
                .setCoverUrl(item.getCoverUrl())).collect(Collectors.toList());
    }
}
