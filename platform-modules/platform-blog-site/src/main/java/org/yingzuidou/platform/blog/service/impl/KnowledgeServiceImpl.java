package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.ArticleRepository;
import org.yingzuidou.platform.blog.dao.KnowledgeRepository;
import org.yingzuidou.platform.blog.dao.ParticipantRepository;
import org.yingzuidou.platform.blog.dao.UserRepository;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.blog.websocket.BlogSocket;
import org.yingzuidou.platform.common.constant.*;
import org.yingzuidou.platform.common.entity.*;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.EntryptionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

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
    private OperRecordService operRecordService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MessageService messageService;


    private static final String SHOWTYPE = "card";


    @Override
    public void addKnowledge(KnowledgeEntity knowledgeEntity) {
        List<KnowledgeEntity> knowledgeEntities = knowledgeRepository
                .findAllByKNameAndIsDelete(knowledgeEntity.getKName(), IsDeleteEnum.NOTDELETE.getValue());
        if (!knowledgeEntities.isEmpty()) {
            throw new BusinessException("知识库名字已存在");
        }

        // 如果访问方式是加密方式，则需要用AES处理
        if (Objects.equals(knowledgeEntity.getKAccess(), AccessEnum.ENCRYPTION.getValue())) {
            knowledgeEntity.setKReserveO(EntryptionUtils.aesEncryption(knowledgeEntity.getKReserveO()));
        }

        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        knowledgeEntity.setCreator(user);
        knowledgeEntity.setCreateTime(new Date());
        knowledgeEntity = knowledgeRepository.save(knowledgeEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.ADD.getValue(), ObjTypeEnum.KNOWLEDGE.getValue(),
                knowledgeEntity.getId(), RootEnum.KNOWLEDGE.getValue(), knowledgeEntity.getId());
    }

    /**
     * 查找当前用户参与的知识库以及自己的知识库
     *
     * @param knowledgeDTO 知识库信息类
     * @return 参与的知识库列表
     */
    @Override
    public List<KnowledgeDTO> list(KnowledgeDTO knowledgeDTO) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<KnowledgeEntity> knowledgeEntities = knowledgeRepository
                .findAllKnowledgeByKParticipantInAndIsDelete(user.getId(), IsDeleteEnum.NOTDELETE.getValue());
        return Optional.ofNullable(knowledgeEntities).orElse(new ArrayList<>()).stream()
                .map(knowledgeEntity -> {
                    KnowledgeDTO tempDTO = new KnowledgeDTO();
                    CmsBeanUtils.copyMorNULLProperties(knowledgeEntity, tempDTO);
                    tempDTO.setCreateName(knowledgeEntity.getCreator().getUserName());
                    if (Objects.equals(knowledgeDTO.getShowWay(), SHOWTYPE)) {
                        List<ArticleEntity> articleEntities = articleRepository
                                .findFirst6ByKnowledgeIdAndIsDeleteOrderByUpdateTimeDesc(
                                        knowledgeEntity.getId(), IsDeleteEnum.NOTDELETE.getValue()
                                );
                        tempDTO.setArticleEntities(articleEntities);
                    }
                    return tempDTO;
                }).collect(Collectors.toList());
    }

    /**
     * 移除知识库的参与者，如果当前执行者不是该知识库的创建者，这没有权限操作
     *
     * @param userId 参与者ID
     * @param knowledgeId 知识库ID
     */
    @Override
    public void removeParticipant(Integer userId, Integer knowledgeId) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("当前用户没有参与任何知识库");
        }
        KnowledgeEntity kEntity =  knowledgeEntityOp.get();
        if (!Objects.equals(kEntity.getCreator().getId(), user.getId())) {
            throw new BusinessException("只有知识库创建者才有权限移除参与者");
        }

        Optional<CmsUserEntity> needRemoveOp =  userRepository.findById(userId);
        if (!needRemoveOp.isPresent()) {
            throw new BusinessException("参与者不存在");
        }

        participantRepository.removeByKnowledgeIdAndParticipantId(knowledgeId, userId);
        noticeParticipantDelete(knowledgeEntityOp.get(), user, userId);
        operRecordService.recordCommonOperation(user, OperTypeEnum.REMOVE.getValue(), ObjTypeEnum.USER.getValue(),
                userId, RootEnum.KNOWLEDGE.getValue(), knowledgeId);
    }

    /**
     * 移除知识库的参与者，拥有共享权限的操作者才能使用该方法无条件移除
     *
     * @param userId 参与者ID
     * @param knowledgeId 知识库ID
     */
    @Override
    public void removeShareParticipant(Integer userId, Integer knowledgeId) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("当前用户没有参与任何知识库");
        }
        Optional<CmsUserEntity> needRemoveOp =  userRepository.findById(userId);
        if (!needRemoveOp.isPresent()) {
            throw new BusinessException("参与者不存在");
        }
        participantRepository.removeByKnowledgeIdAndParticipantId(knowledgeId, userId);
        noticeParticipantDelete(knowledgeEntityOp.get(), user, userId);
        operRecordService.recordCommonOperation(user, OperTypeEnum.REMOVE.getValue(), ObjTypeEnum.USER.getValue(),
                userId, RootEnum.KNOWLEDGE.getValue(), knowledgeId);
    }

    /**
     * 获取单个知识库的基本信息以及知识库参与者信息
     *
     * @param knowledgeId 知识库ID
     * @return 知识库信息对象
     */
    @Override
    public KnowledgeDTO item(Integer knowledgeId) {
        KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (knowledgeEntityOp.isPresent()) {
            KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
            CmsBeanUtils.copyMorNULLProperties(knowledgeEntity, knowledgeDTO);
            // 获取知识库参与者
            List<ParticipantEntity> participantEntities = participantRepository.findAllByKnowledgeId(knowledgeId);
            knowledgeDTO.setParticipantEntities(participantEntities);
        }

        return knowledgeDTO;
    }

    /**
     * <p>移除指定的知识库，如果知识库下存在文章，那么需要删除文章在移除。需要发送通知给该知识库参与者告知知识库已经移除
     * 拥有知识库删除权限的用户可以移除其他人的知识库
     *
     * @param knowledgeId 知识库ID
     *
     */
    @Override
    public void removeKnowledge(Integer knowledgeId) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("需要删除的知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!Objects.equals(user.getId(), knowledgeEntity.getCreator().getId())) {
            throw new BusinessException("没有权限移除其他人的知识库");
        }
        if (articleRepository.existsByKnowledgeIdAndIsDelete(knowledgeId, IsDeleteEnum.NOTDELETE.getValue())) {
            throw new BusinessException("知识库下存在文章，请先迁移文章");
        }
        knowledgeEntity.setIsDelete(IsDeleteEnum.DELETE.getValue()).setUpdator(user.getId()).setUpdateTime(new Date());
        knowledgeRepository.save(knowledgeEntity);

        List<ParticipantEntity> participantEntities = participantRepository.findAllByKnowledgeId(knowledgeId);
        if (!participantEntities.isEmpty()) {
            participantEntities.forEach(item -> noticeKnowledgeDelete(knowledgeEntity, user, item.getParticipant().getId()));
        }

        participantRepository.removeAllParticipantByKnowledgeId(knowledgeId);
    }

    @Override
    public void removeShareKnowledge(Integer knowledgeId) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("需要删除的知识库不存在");
        }
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();

        if (articleRepository.existsByKnowledgeIdAndIsDelete(knowledgeId, IsDeleteEnum.NOTDELETE.getValue())) {
            throw new BusinessException("知识库下存在文章，请先迁移文章");
        }
        knowledgeEntity.setIsDelete(IsDeleteEnum.DELETE.getValue()).setUpdator(user.getId()).setUpdateTime(new Date());
        knowledgeRepository.save(knowledgeEntity);

        List<ParticipantEntity> participantEntities = participantRepository.findAllByKnowledgeId(knowledgeId);
        if (!participantEntities.isEmpty()) {
            participantEntities.forEach(item -> noticeKnowledgeDelete(knowledgeEntity, user, item.getParticipant().getId()));
            if (!Objects.equals(knowledgeEntity.getCreator().getId(), user.getId())) {
                noticeKnowledgeDelete(knowledgeEntity, user, knowledgeEntity.getCreator().getId());
            }
        }

        participantRepository.removeAllParticipantByKnowledgeId(knowledgeId);
    }

    /**
     * 更新知识库，只有知识库拥有者才有权限更新知识库
     *
     * @param knowledgeEntity 知识库内容
     */
    @Override
    public void editKnowledge(KnowledgeEntity knowledgeEntity) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeEntity.getId());
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("需要更新的知识库不存在");
        }
        KnowledgeEntity origin = knowledgeEntityOp.get();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (!Objects.equals(origin.getCreator().getId(), user.getId())) {
            throw new BusinessException("无权限更新他人的知识库");
        }

        CmsBeanUtils.copyMorNULLProperties(knowledgeEntity, origin);
        origin.setUpdator(user.getId());
        origin.setUpdateTime(new Date());
        knowledgeRepository.save(origin);

        List<ParticipantEntity> participantEntities = participantRepository.findAllByKnowledgeId(origin.getId());
        if (!participantEntities.isEmpty()) {
            participantEntities.forEach(item -> noticeKnowledgeEdit(knowledgeEntity, user, item.getParticipant().getId()));
        }

        operRecordService.recordCommonOperation(user, OperTypeEnum.EDIT.getValue(), ObjTypeEnum.KNOWLEDGE.getValue(),
                origin.getId(), RootEnum.KNOWLEDGE.getValue(), origin.getId());
    }

    /**
     * 共享更新知识库，拥有共享更新知识库权限用户无权限更新知识库
     *
     * @param knowledgeEntity 知识库内容
     */
    @Override
    public void editShareKnowledge(KnowledgeEntity knowledgeEntity) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeEntity.getId());
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("需要更新的知识库不存在");
        }
        KnowledgeEntity origin = knowledgeEntityOp.get();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");

        CmsBeanUtils.copyMorNULLProperties(knowledgeEntity, origin);
        origin.setUpdator(user.getId());
        origin.setUpdateTime(new Date());
        knowledgeRepository.save(origin);

        List<ParticipantEntity> participantEntities = participantRepository.findAllByKnowledgeId(origin.getId());
        if (!participantEntities.isEmpty()) {
            participantEntities.forEach(item -> noticeKnowledgeEdit(knowledgeEntity, user, item.getParticipant().getId()));
            if (!Objects.equals(knowledgeEntity.getCreator().getId(), user.getId())) {
                noticeKnowledgeEdit(knowledgeEntity, user, knowledgeEntity.getCreator().getId());
            }
        }

        operRecordService.recordCommonOperation(user, OperTypeEnum.EDIT.getValue(), ObjTypeEnum.KNOWLEDGE.getValue(),
                origin.getId(), RootEnum.KNOWLEDGE.getValue(), origin.getId());
    }

    /**
     * 获取当前用户所参与的所有知识库，包括自己创建的知识库
     *
     * @return 所参与的知识库列表
     */
    @Override
    public List<KnowledgeDTO> retrieveParticipantKnowledge() {
        List<KnowledgeDTO> result = new ArrayList<>();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<KnowledgeEntity> knowledgeEntities = knowledgeRepository
                .findAllKnowledgeByKParticipantInAndIsDelete(user.getId(), IsDeleteEnum.NOTDELETE.getValue());
        if (!knowledgeEntities.isEmpty()) {
            result = knowledgeEntities.stream().map(knowledge -> {
                KnowledgeDTO knowledgeDTO = new KnowledgeDTO();
                knowledgeDTO.setId(knowledge.getId()).setKName(knowledge.getKName());
                return knowledgeDTO;
            }) .collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public boolean stillUsingCategory(Integer categoryId) {
        Integer counts = knowledgeRepository.findAllByKTypeAndIsDelete(categoryId,
                IsDeleteEnum.NOTDELETE.getValue());
        return counts != 0;
    }

    private void noticeParticipantDelete(KnowledgeEntity knowledgeEntity, CmsUserEntity opUser, Integer userId) {
        String message = String.format("您已经被%s从知识库[%s]中开除啦", opUser.getUserName(),
                knowledgeEntity.getKName());
        messageService.addMessage(MessageTypeEnum.REMOVEPARTICIPANT.getValue(), message, userId);
        BlogSocket.sendSpecifyUserMsg(userId, WebSocketTypeEnum.NOTICE, "new");
    }

    private void noticeKnowledgeDelete(KnowledgeEntity knowledgeEntity, CmsUserEntity opUser, Integer userId) {
        String message = String.format("%s删除了知识库[%s]", opUser.getUserName(),
                knowledgeEntity.getKName());
        messageService.addMessage(MessageTypeEnum.KNOWLEDGEDEL.getValue(), message, userId);
        BlogSocket.sendSpecifyUserMsg(userId, WebSocketTypeEnum.NOTICE, "new");
    }

    private void noticeKnowledgeEdit(KnowledgeEntity knowledgeEntity, CmsUserEntity opUser, Integer userId) {
        String message = String.format("%s修改了知识库[%s]信息", opUser.getUserName(),
                knowledgeEntity.getKName());
        messageService.addMessage(MessageTypeEnum.KNOWLEDGEEDIT.getValue(), message, userId);
        BlogSocket.sendSpecifyUserMsg(userId, WebSocketTypeEnum.NOTICE, "new");
    }
}
