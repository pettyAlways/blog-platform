package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.constant.IsDeleteEnum;
import org.yingzuidou.platform.blog.dao.*;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.*;
import org.yingzuidou.platform.blog.websocket.BlogSocket;
import org.yingzuidou.platform.common.constant.*;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
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
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private OperRecordService operRecordService;

    @Autowired
    private RecentEditService recentEditService;

    @Autowired
    private ArticleParticipantService articleParticipantService;

    @Autowired
    private MessageService messageService;

    /**
     * 查找知识库下所有的文章，按照发布时间排序
     *
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
        if (!Objects.equals(user.getId(), knowledgeEntity.getCreator())) {
           boolean exist = participantRepository.existsByKnowledgeIdAndParticipantId(knowledgeId, user.getId());
           if (!exist) {
               throw new BusinessException("当前用户没有加入该知识库，无法查询文章");
           }
        }
        List<Object[]> data =  articleRepository.findArticleListInKnowledge(knowledgeId, IsDeleteEnum.NOTDELETE.getValue());
        if (!data.isEmpty()) {
            return data.stream().map(item -> ArticleDTO.articleListInKnowledge.apply(item)).collect(Collectors.toList());
        }
        return null;
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
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(articleEntity.getKnowledgeId(),
                IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("请在指定的知识库下增加文章");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        boolean exist = participantRepository.existsByKnowledgeIdAndParticipantId(articleEntity.getKnowledgeId(), user.getId());
        if (!Objects.equals(knowledgeEntity.getCreator(), user.getId()) && !exist) {
            throw new BusinessException("当前用户没有加入该知识库，无法新增文章");
        }
        articleEntity.setPostTime(new Date());
        articleEntity.setAuthorId(user.getId());
        articleEntity.setCreator(user.getId());
        articleEntity.setCreateTime(new Date());
        articleEntity = articleRepository.save(articleEntity);
        // 新增一篇文章就对知识库做了修改，需要更新数据库的修改时间，来用于知识库页面的知识库列表排序
        knowledgeEntity.setEditTime(new Date());
        knowledgeRepository.save(knowledgeEntity);
        recentEditService.saveRecentEditRecord(user.getId(), articleEntity, knowledgeEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.ADD.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                articleEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());
        return articleEntity;
    }


    /**
     *  获取文章内容以及所有的文章协作者、知识库信息、分类信息
     *
     * @param articleId 文章ID
     * @return 文章信息
     */
    @Override
    public ArticleDTO getArticle(Integer articleId) {
        List<Object[]> data = articleRepository.findArticleShowInfo(articleId, IsDeleteEnum.NOTDELETE.getValue());
        if (data.isEmpty()) {
            throw new BusinessException("所查询的文章不存在");
        }

        ArticleDTO articleDTO = ArticleDTO.articleShow.apply(data.get(0));
        List<CmsUserEntity> participantList = articleParticipantService.listParticipantByArticleId(articleId);
        if (!participantList.isEmpty()) {
            List<UserDTO> userDTOList = participantList.stream().map(item -> {
                UserDTO user = new UserDTO();
                user.setId(item.getId()).setUserName(item.getUserName());
                return user;
            }).collect(Collectors.toList());
            articleDTO.setParticipantList(userDTOList);
        }
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
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(articleEntity.getKnowledgeId(),
                IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("所修改的文章不在任何知识库下");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");

        Optional<ArticleEntity> articleEntityOp = articleRepository.findById(articleEntity.getId());
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("所修改的文章不存在");
        }
        ArticleEntity origin = articleEntityOp.get();
        // 修改人和文章的创建人是否是同一个人或者是知识库拥有者
        if (!Objects.equals(origin.getAuthorId(), user.getId())
                && !Objects.equals(user.getId(), knowledgeEntity.getCreator())) {
            throw new BusinessException("没有权限修改其他人文章");
        }
        String oldTitle = origin.getArticleTitle();
        CmsBeanUtils.copyMorNULLProperties(articleEntity, origin);
        origin.setUpdateTime(new Date());
        origin.setUpdator(user.getId());
        articleRepository.save(origin);
        // 更新知识库日期来在知识库列表中根据更新时间排序
        knowledgeEntity.setEditTime(new Date());
        knowledgeRepository.save(knowledgeEntity);
        // 如果修改其他人的文章需要添加参与者，以及发送消息给文章创建者
        if (!Objects.equals(origin.getAuthorId(), user.getId())) {
            articleParticipantService.addArticleParticipant(origin.getId(), user);
            noticeArticleEdit(knowledgeEntity, user, origin, oldTitle);
        }
        recentEditService.saveRecentEditRecord(user.getId(), origin, knowledgeEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.EDIT.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                origin.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());

        articleEntity.setId(origin.getId());
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
        KnowledgeEntity knowledgeEntity = knowledgeRepository.findByIdAndIsDelete(articleEntity.getKnowledgeId(),
                IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("所修改的文章不在任何知识库下");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");

        Optional<ArticleEntity> articleEntityOp = articleRepository.findById(articleEntity.getId());
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("所修改的文章不存在");
        }
        ArticleEntity origin = articleEntityOp.get();
        String oldTitle = origin.getArticleTitle();
        CmsBeanUtils.copyMorNULLProperties(articleEntity, origin);
        origin.setUpdateTime(new Date());
        origin.setUpdator(user.getId());
        articleRepository.save(origin);
        // 更新知识库日期来在知识库列表中根据更新时间排序
        knowledgeEntity.setEditTime(new Date());
        knowledgeRepository.save(knowledgeEntity);
        // 如果修改其他人的文章需要添加参与者，以及发送消息给文章创建者
        if (!Objects.equals(origin.getAuthorId(), user.getId())) {
            articleParticipantService.addArticleParticipant(origin.getId(), user);
            noticeArticleEdit(knowledgeEntity, user, origin, oldTitle);
        }
        recentEditService.saveRecentEditRecord(user.getId(), origin, knowledgeEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.EDIT.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                origin.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());

        articleEntity.setId(origin.getId());
        return articleEntity;
    }

    /**
     * 1.删除知识库下的文章，如果操作用户不是拥有共享文章删除权限或者知识库拥有者，则没有权限删除别人的文章
     * 2.需要移除该文章的所有参与者
     * 3.移除该文章在最近编辑表中的及记录
     * 4.需要增加一条操作记录
     *
     * @param articleId 文章ID
     * @return 知识库下第一篇文章内容
     */
    @Override
    public ArticleDTO deleteArticle(Integer articleId) {
        Optional<ArticleEntity> articleEntityOp =  articleRepository.findById(articleId);
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("要删除的文章不存在");
        }
        ArticleEntity articleEntity = articleEntityOp.get();
        KnowledgeEntity knowledgeEntity = knowledgeRepository.
                findByIdAndIsDelete(articleEntity.getKnowledgeId(), IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("需要删除的文章不在任何知识库下");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (!Objects.equals(user.getId(), articleEntity.getAuthorId())
                && !Objects.equals(user.getId(), knowledgeEntity.getCreator())) {
            throw new BusinessException("没有权限删除其他人的文章");
        }

        articleEntity.setUpdateTime(new Date());
        articleEntity.setUpdator(user.getId());
        articleEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        articleRepository.save(articleEntity);
        knowledgeEntity.setEditTime(new Date());
        knowledgeRepository.save(knowledgeEntity);

        if (!Objects.equals(user.getId(), articleEntity.getAuthorId())) {
            noticeArticleDelete(knowledgeEntity, user,  articleEntity, articleEntity.getArticleTitle());
        }
        articleParticipantService.removeAllArticleParticipantInArticle(articleId);
        recentEditService.removeRecentArticleRecord(articleId);
        operRecordService.recordCommonOperation(user, OperTypeEnum.DEL.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                articleEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId(), articleEntity.getArticleTitle());

        articleEntity = articleRepository.findFirstByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(
                articleEntity.getKnowledgeId(), IsDeleteEnum.NOTDELETE.getValue());
        ArticleDTO articleDTO = new ArticleDTO();
        if (Objects.nonNull(articleEntity)) {
            articleDTO.setArticleId(articleEntity.getId());
        }
        return articleDTO;
    }

    @Override
    public ArticleDTO deleteShareArticle(Integer articleId) {
        Optional<ArticleEntity> articleEntityOp =  articleRepository.findById(articleId);
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("要删除的文章不存在");
        }
        ArticleEntity articleEntity = articleEntityOp.get();
        KnowledgeEntity knowledgeEntity = knowledgeRepository.
                findByIdAndIsDelete(articleEntity.getKnowledgeId(), IsDeleteEnum.NOTDELETE.getValue());
        if (Objects.isNull(knowledgeEntity)) {
            throw new BusinessException("需要删除的文章不在任何知识库下");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");

        articleEntity.setUpdateTime(new Date());
        articleEntity.setUpdator(user.getId());
        articleEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        articleRepository.save(articleEntity);
        knowledgeEntity.setEditTime(new Date());
        knowledgeRepository.save(knowledgeEntity);

        if (!Objects.equals(user.getId(), articleEntity.getAuthorId())) {
            noticeArticleDelete(knowledgeEntity, user,  articleEntity, articleEntity.getArticleTitle());
        }

        articleParticipantService.removeAllArticleParticipantInArticle(articleId);
        recentEditService.removeRecentArticleRecord(articleId);
        operRecordService.recordCommonOperation(user, OperTypeEnum.DEL.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                articleEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId(), articleEntity.getArticleTitle());

        articleEntity = articleRepository.findFirstByKnowledgeIdAndIsDeleteOrderByPostTimeAsc(
                articleEntity.getKnowledgeId(), IsDeleteEnum.NOTDELETE.getValue());
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setArticleId(articleEntity.getId());
        return articleDTO;
    }

    /**
     * 将文章复制到指定的知识库下面
     *
     * @param articleId 目标文章ID
     * @param knowledgeId 目的知识库ID
     */
    @Override
    public void copyTo(Integer articleId, Integer knowledgeId) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("文章无法复制,目标知识库不存在");
        }
        Optional<ArticleEntity> articleEntityOp = articleRepository.findById(articleId);
        if (!articleEntityOp.isPresent()) {
            throw new BusinessException("文章不存在，无法复制");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        ArticleEntity origin = articleEntityOp.get();
        ArticleEntity copy = new ArticleEntity();
        copy.setArticleTitle(origin.getArticleTitle()).setContent(origin.getContent()).setAuthorId(user.getId())
                .setPostTime(new Date()).setCreator(user.getId()).setCreateTime(new Date()).setKnowledgeId(knowledgeId)
                .setUpdateTime(new Date());
        copy = articleRepository.save(copy);
        recentEditService.saveRecentEditRecord(user.getId(), copy, knowledgeEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.ADD.getValue(), ObjTypeEnum.ARTICLE.getValue(),
                copy.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());
    }

    private void noticeArticleEdit(KnowledgeEntity knowledgeEntity, CmsUserEntity user, ArticleEntity origin, String oldTitle) {
        String message = String.format("%s在知识库[%s]下修改了您的文章《%s》,请尽快核实内容", user.getUserName(),
                knowledgeEntity.getKName(), oldTitle);
        Map<String, Integer> map = new HashMap<>(3);
        map.put("articleId", origin.getId());
        map.put("knowledgeId", knowledgeEntity.getId());
        messageService.addMessage(MessageTypeEnum.ARTICLEEDIT.getValue(), message, origin.getAuthorId(),
                CmsBeanUtils.beanToJson(map));
        Integer counts = messageService.countOfMessage(origin.getAuthorId());
        BlogSocket.sendSpecifyUserMsg(origin.getAuthorId(), WebSocketTypeEnum.NOTICE, counts);
    }

    private void noticeArticleDelete(KnowledgeEntity knowledgeEntity, CmsUserEntity user, ArticleEntity origin, String oldTitle) {
        String message = String.format("%s在知识库[%s]下删除了您的文章《%s》,请尽快核实内容", user.getUserName(),
                knowledgeEntity.getKName(), oldTitle);
        Map<String, Integer> map = new HashMap<>(1);
        map.put("knowledgeId", knowledgeEntity.getId());
        messageService.addMessage(MessageTypeEnum.ARTICLEDEL.getValue(), message, origin.getAuthorId(),
                CmsBeanUtils.beanToJson(map));
        Integer counts = messageService.countOfMessage(origin.getAuthorId());
        BlogSocket.sendSpecifyUserMsg(origin.getAuthorId(), WebSocketTypeEnum.NOTICE, counts);
    }
}
