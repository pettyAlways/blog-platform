package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.constant.IsDeleteEnum;
import org.yingzuidou.platform.blog.dao.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.service.ArticleService;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.blog.service.RecentEditService;
import org.yingzuidou.platform.common.constant.ObjTypeEnum;
import org.yingzuidou.platform.common.constant.OperTypeEnum;
import org.yingzuidou.platform.common.constant.RootEnum;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.RecentEditEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
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
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleBakRepository articleBakRepository;

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private OperRecordService operRecordService;

    @Autowired
    private RecentEditService recentEditService;

    /**
     * <p>查找知识库下的文章目录信息,业务相关有如下几点
     * <ul>
     *     <li>校验知识库是否存在</li>
     *     <li>校验当前操作人是否参与该知识库，没有参与则不能查找</li>
     *     <li>如果备份文章列表中存在知识库下的一些文章，这些文章需要获取审核的状态</li>
     *     <li>返回目录显示的关键信息：文章标题，作者，发布时间，状态</li>
     * </ul>
     * @param knowledgeId 知识库ID
     * @return 知识库下的文章目录
     */
    @Override
    public List<ArticleDTO> findArticleByKnowledge(Integer knowledgeId) {
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(knowledgeId, IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (!Objects.equals(user.getId(), knowledgeEntity.getCreator().getId())) {
           boolean exist = participantRepository.existsByKnowledgeIdAndParticipantId(knowledgeId, user.getId());
           if (!exist) {
               throw new BusinessException("当前用户没有加入该知识库，无法查询文章");
           }
        }
        List<ArticleEntity> articleEntities =  articleRepository
                .findAllByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(knowledgeId, IsDeleteEnum.NOTDELETE.getValue());

        return Optional.ofNullable(articleEntities).orElse(new ArrayList<>()).stream().map(item -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(item.getId());
            articleDTO.setArticleTitle(item.getArticleTitle());
            articleDTO.setCreateName(item.getCreator().getUserName());
            articleDTO.setPostTime(item.getPostTime());
            return articleDTO;
        }).collect(Collectors.toList());
    }

    /**
     * <p>在指定的知识库下新增文章，有下面几点业务需求
     * <ul>
     *     <li>校验当前操作人是否有权限在知识库下新增文章</li>
     *     <li></li>
     * </ul>
     * @param articleEntity 文章实体类
     */
    @Override
    public ArticleEntity addArticle(ArticleEntity articleEntity) {
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(articleEntity.getKnowledge().getId(),
                IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (!Objects.equals(user.getId(), knowledgeEntity.getCreator().getId())) {
            boolean exist = participantRepository.existsByKnowledgeIdAndParticipantId(articleEntity.getKnowledge().getId(),
                    user.getId());
            if (!exist) {
                throw new BusinessException("当前用户没有加入该知识库，无法新增文章");
            }
        }
        articleEntity.setPostTime(new Date());
        articleEntity.setAuthor(user);
        articleEntity.setCreator(user);
        articleEntity = articleRepository.save(articleEntity);
        recentEditService.saveRecentEditRecord(user.getId(), articleEntity, knowledgeEntity);
        operRecordService.recordCommonOperation(user.getId(), OperTypeEnum.ADD.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                articleEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());
        return articleEntity;
    }


    /**
     * <p>根据文章ID获取文章信息,有如下几点需求
     * <ul>
     *     <li>校验文章是否存在</li>
     *     <li>获取内容：文章内容、知识库名、分类、创建者、协作者等</li>
     * </ul>
     *
     * @param articleId 文章ID
     * @return
     */
    @Override
    public ArticleDTO getArticle(Integer articleId) {
        ArticleEntity articleEntity = articleRepository.findByIdAndIsDelete(articleId, IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(articleEntity)) {
            throw new BusinessException("所查询的文章不存在");
        }
        ArticleDTO articleDTO = new ArticleDTO();
        articleTransformDTO(articleEntity, articleDTO);

        return articleDTO;
    }

    /**
     * <p>修改文章内容，有如下的加点需求
     * <ul>
     *     <li>需要校验所在的文章知识是否存在</li>
     *     <li>校验当前修改人是否是知识库的创建者，不是那么判断他是否加入该知识库，没有加入无权修改</li>
     *     <li>校验修改的文章是否存在</li>
     *     <li>校验当前修改人是否是该文章的创建者，其他人不能对不是自己的文章做修改</li>
     *     <li>拥有修改其他人文章权限的人请使用{@link #editShareArticle}方法</li>
     * </ul>
     *
     * @param articleEntity 修改的文章内容
     * @return 修改后的文章
     */
    @Override
    public ArticleEntity editArticle(ArticleEntity articleEntity) {
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(articleEntity.getKnowledge().getId(),
                IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (!Objects.equals(user.getId(), knowledgeEntity.getCreator().getId())) {
            boolean exist = participantRepository.existsByKnowledgeIdAndParticipantId(articleEntity.getKnowledge().getId(),
                    user.getId());
            if (!exist) {
                throw new BusinessException("当前用户没有加入该知识库，无法编辑文章");
            }
        }
        Optional<ArticleEntity> articleEntityOp = articleRepository.findById(articleEntity.getId());
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("所修改的文章不存在");
        }
        ArticleEntity origin = articleEntityOp.get();
        // 修改人和文章的创建人是否是同一个人
        if (!Objects.equals(origin.getCreator().getId(), user.getId())) {
            throw new BusinessException("没有权限修改其他人文章");
        }
        CmsBeanUtils.copyMorNULLProperties(articleEntity, origin);
        origin.setUpdateTime(new Date());
        origin.setUpdator(user);
        articleRepository.save(origin);
        articleEntity.setId(origin.getId());
        recentEditService.saveRecentEditRecord(user.getId(), origin, knowledgeEntity);
        operRecordService.recordCommonOperation(user.getId(), OperTypeEnum.EDIT.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                articleEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());
        return articleEntity;
    }

    /**
     * 共享修改文章的方法，只有拥有共享权限的人才能修改
     *
     * @param articleEntity 修改文章
     * @return 修改后的文章内容
     */
    @Override
    public ArticleEntity editShareArticle(ArticleEntity articleEntity) {
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(articleEntity.getKnowledge().getId(),
                IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (!Objects.equals(user.getId(), knowledgeEntity.getCreator().getId())) {
            boolean exist = participantRepository.existsByKnowledgeIdAndParticipantId(articleEntity.getKnowledge().getId(),
                    user.getId());
            if (!exist) {
                throw new BusinessException("当前用户没有加入该知识库，无法编辑文章");
            }
        }
        Optional<ArticleEntity> articleEntityOp = articleRepository.findById(articleEntity.getId());
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("所修改的文章不存在");
        }
        ArticleEntity origin = articleEntityOp.get();
        CmsBeanUtils.copyMorNULLProperties(articleEntity, origin);
        origin.setUpdateTime(new Date());
        origin.setUpdator(user);
        articleRepository.save(origin);
        articleEntity.setId(origin.getId());
        recentEditService.saveRecentEditRecord(user.getId(), origin, knowledgeEntity);
        operRecordService.recordCommonOperation(user.getId(), OperTypeEnum.EDIT.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                articleEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());
        return articleEntity;
    }

    @Override
    public ArticleDTO deleteArticle(Integer articleId) {
        Optional<ArticleEntity> articleEntityOp =  articleRepository.findById(articleId);
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("要删除的文章不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        ArticleEntity articleEntity = articleEntityOp.get();
        if (!Objects.equals(user.getId(), articleEntity.getCreator().getId())) {
            throw new BusinessException("没有权限删除其他人的文章");
        }
        articleEntity.setUpdateTime(new Date());
        articleEntity.setUpdator(user);
        articleEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        articleRepository.save(articleEntity);
        recentEditService.removeRecentArticleRecord(articleId);
        articleEntity = articleRepository.findFirstByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(
                articleEntity.getKnowledge().getId(), IsDeleteEnum.NOTDELETE.getValue());
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(articleEntity.getId());
        return articleDTO;
    }

    @Override
    public ArticleDTO deleteShareArticle(Integer articleId) {
        Optional<ArticleEntity> articleEntityOp =  articleRepository.findById(articleId);
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("要删除的文章不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        ArticleEntity articleEntity = articleEntityOp.get();
        articleEntity.setUpdateTime(new Date());
        articleEntity.setUpdator(user);
        articleEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        articleRepository.save(articleEntity);
        recentEditService.removeRecentArticleRecord(articleId);
        // TODO 发通知给创建者
        articleEntity = articleRepository.findFirstByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(
                articleEntity.getKnowledge().getId(), IsDeleteEnum.NOTDELETE.getValue());
        ArticleDTO articleDTO =  new ArticleDTO();
        if (Objects.nonNull(articleEntity)) {
            articleDTO.setId(articleEntity.getId());
        }
        return articleDTO;
    }

    private void articleTransformDTO(ArticleEntity articleEntity, ArticleDTO articleDTO) {
        articleDTO.setCreateName(articleEntity.getAuthor().getUserName());
        articleDTO.setCreatorId(articleEntity.getAuthor().getId());
        articleDTO.setArticleTitle(articleEntity.getArticleTitle());
        articleDTO.setContent(articleEntity.getContent());
        articleDTO.setPostTime(articleEntity.getPostTime());
        articleDTO.setCategoryName(articleEntity.getKnowledge().getkType().getCategoryName());
        articleDTO.setCategoryId(articleEntity.getKnowledge().getkType().getId());
        articleDTO.setKnowledgeId(articleEntity.getKnowledge().getId());
        articleDTO.setKnowledgeName(articleEntity.getKnowledge().getkName());
    }
}
