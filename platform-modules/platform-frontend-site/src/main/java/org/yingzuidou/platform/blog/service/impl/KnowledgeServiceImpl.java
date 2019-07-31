package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.auth.client.core.util.JwtTokenUtil;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.ArticleRepository;
import org.yingzuidou.platform.blog.dao.AuditRepository;
import org.yingzuidou.platform.blog.dao.KnowledgeRepository;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.blog.service.ParticipantService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.constant.*;
import org.yingzuidou.platform.common.entity.*;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.EntryptionUtils;
import org.yingzuidou.platform.common.utils.PasswordJwtUtil;

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

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuditRepository auditRepository;

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
     * @return 最近创建的8个知识库
     */
    @Override
    public List<KnowledgeDTO> listRecent() {
        List<KnowledgeEntity> knowledgeEntities = knowledgeRepository
                .findFirst8ByKAccessAndIsDeleteOrderByEditTimeDesc(AccessEnum.PUBLIC.getValue(), IsDeleteEnum.NOTDELETE.getValue());
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
        List<Object[]> knowledgeEntities = knowledgeRepository
                .findAllByKTypeAndIsDelete(categoryId, IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable());
        return Optional.ofNullable(knowledgeEntities).orElse(new ArrayList<>()).stream()
                .map(item ->KnowledgeDTO.categoryKnowledgeList.apply(item)).collect(Collectors.toList());
    }

    /**
     * 查找分页下可访问的知识库
     *
     * @param categoryId 分类ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    @Override
    public List<KnowledgeDTO> listCouldAccessKnowledgeByCategory(Integer categoryId, PageInfo pageInfo) {
        List<Object[]> knowledgeEntities = knowledgeRepository
                .findCouldAccessKnowledgeByCategory(categoryId, IsDeleteEnum.NOTDELETE.getValue(), pageInfo.toPageable());
        return Optional.ofNullable(knowledgeEntities).orElse(new ArrayList<>()).stream()
                .map(item ->KnowledgeDTO.relateKnowledgeList.apply(item)).collect(Collectors.toList());
    }

    /**
     * 获取知识库详情内容
     *
     * @param knowledgeId 知识库ID
     * @param token 加密的知识库需要token验证访问
     * @param userId 访问用户
     * @return 知识库详情
     */
    @Override
    public KnowledgeDTO retrieveKnowledgeDetail(Integer knowledgeId, String token, Integer userId) {
        KnowledgeDTO knowledgeDTO;
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!PasswordJwtUtil.verifyKnowledgeEncrypt(knowledgeEntity, userId, token)) {
            return null;
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

    /**
     * 申请加入知识库
     *
     * @param knowledgeId 知识库ID
     * @param reason 加入原因
     */
    @Override
    public void joinKnowledge(Integer knowledgeId, String reason) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        if (Objects.equals(knowledgeEntity.getCreator(), cmsUserEntity.getId())) {
            throw new BusinessException("您是该知识库的创建者，不需要申请加入");
        }
        List<UserDTO> participants = participantService.findAllParticipantInKnowledge(knowledgeId);
        List<Integer> userId = Optional.ofNullable(participants).orElse(new ArrayList<>()).stream()
                .map(UserDTO::getUserId).collect(Collectors.toList());
        if (userId.contains(cmsUserEntity.getId())) {
            throw new BusinessException("您已经参与该知识库了");
        }

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setReason(reason);
        auditEntity.setHandleUser(knowledgeEntity.getCreator()).setApplyType(AuditEnum.JOIN.getValue())
                .setApplyTime(new Date()).setApplyUser(cmsUserEntity.getId()).setApplyObj(knowledgeEntity.getId());
        auditRepository.save(auditEntity);
        messageService.addMessage(MessageTypeEnum.JOINKNOWLEDGE.getValue(),
                cmsUserEntity.getUserName() + "申请加入知识库[" + knowledgeEntity.getKName()+ "]",
                knowledgeEntity.getCreator());
    }

    /**
     * 判断用户是否已经是知识库的参与者
     *
     * @param knowledgeId 知识库ID
     * @return true 已经参与 false 还未参与
     */
    @Override
    public boolean alreadyJoinKnowledge(Integer knowledgeId) {
        CmsUserEntity cmsUserEntity = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity target = knowledgeEntityOp.get();
        if (Objects.equals(target.getCreator(), cmsUserEntity.getId())) {
            return true;
        }
        List<UserDTO> userDTOList = participantService.findAllParticipantInKnowledge(knowledgeId);
        List<UserDTO> participantUser = Optional.ofNullable(userDTOList).orElse(new ArrayList<>()).stream()
                .filter(item -> Objects.equals(item.getUserId(), cmsUserEntity.getId())).collect(Collectors.toList());
        return !participantUser.isEmpty();
    }

    /**
     * 知识库密码校验
     *
     * @param knowledgeId 知识库ID
     * @param password 输入的密码
     * @return true 通过 false 不通过
     */
    @Override
    public String passwordVerify(Integer knowledgeId, String password) {
        String token = null;
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
          throw new BusinessException("知识库不存在");
        }
        KnowledgeEntity knowledgeEntity = knowledgeEntityOp.get();
        if (!Objects.equals(knowledgeEntity.getKAccess(), AccessEnum.ENCRYPTION.getValue())) {
            throw new BusinessException("知识库未加密");
        }
        String raw = EntryptionUtils.aesDecryption(knowledgeEntity.getKReserveO());
        if (Objects.equals(raw, password)) {
            token = PasswordJwtUtil.generateToken(knowledgeEntity.getId(), knowledgeEntity.getKName(),
                    knowledgeEntity.getKReserveO());
        }
        return token;
    }
}
