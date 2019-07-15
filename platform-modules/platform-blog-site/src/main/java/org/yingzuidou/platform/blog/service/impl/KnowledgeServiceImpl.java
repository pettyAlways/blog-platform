package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.ArticleRepository;
import org.yingzuidou.platform.blog.dao.KnowledgeRepository;
import org.yingzuidou.platform.blog.dao.ParticipantRepository;
import org.yingzuidou.platform.blog.dao.UserRepository;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.blog.service.OperRecordService;
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
     */
    @Override
    public void removeParticipant(Integer userId, Integer knowledgeId) {
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("当前的知识库不存在，知识库ID为[" + knowledgeId + "]");
        }
        KnowledgeEntity kEntity =  knowledgeEntityOp.get();
        if (!Objects.equals(kEntity.getCreator().getId(), user.getId())) {
            throw new BusinessException("只有知识库创建者才有权限移除参与者");
        }

        Optional<CmsUserEntity> needRemoveOp =  userRepository.findById(userId);
        if (!needRemoveOp.isPresent()) {
            throw new BusinessException("需要移除的参与者不存在，参与者ID为[" + userId + "]");
        }

        participantRepository.removeByKnowledgeIdAndParticipantId(knowledgeId, userId);
        operRecordService.recordCommonOperation(user, OperTypeEnum.REMOVE.getValue(), ObjTypeEnum.USER.getValue(),
                knowledgeId, RootEnum.KNOWLEDGE.getValue(), knowledgeId);
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
            // TODO 通知参与者关于知识库的删除
        }

        participantRepository.removeAllParticipantByKnowledgeId(knowledgeId);
    }

}
