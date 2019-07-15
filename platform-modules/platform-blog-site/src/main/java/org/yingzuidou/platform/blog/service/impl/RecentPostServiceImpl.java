package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.*;
import org.yingzuidou.platform.blog.dto.RecentPostDTO;
import org.yingzuidou.platform.blog.service.RecentPostService;
import org.yingzuidou.platform.common.constant.IsDeleteEnum;
import org.yingzuidou.platform.common.constant.ObjTypeEnum;
import org.yingzuidou.platform.common.constant.OperTypeEnum;
import org.yingzuidou.platform.common.constant.RootEnum;
import org.yingzuidou.platform.common.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class RecentPostServiceImpl implements RecentPostService {

    @Autowired
    private OperRecordRepository operRecordRepository;

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * <p>查询最近的动态，有下面的需求
     * <ul>
     *     <li>当前用户是根类型对象的创建者则获取其下的所有动态</li>
     *     <li>当前用户是根类型对象的参与者，则获取该根类型下的所有动态</li>
     * </ul>
     * <note>根类型就是最上层的模块，类似知识库就是所有文章操作的根类型</note>
     *
     * @return 动态信息
     */
    @Override
    public List<RecentPostDTO> getRecentPost() {
        return getKnowledgeRelateRecentPost();
    }

    private List<RecentPostDTO> getKnowledgeRelateRecentPost() {
        List<RecentPostDTO> recentPostDTOS;
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<Integer> knowledgeIdList = knowledgeRepository
                .findAllByKParticipantInAndIsDelete(user.getId(), IsDeleteEnum.NOTDELETE.getValue());
        List<OperRecordEntity> operRecordEntities = operRecordRepository.findRecentPost(
                RootEnum.KNOWLEDGE.getValue(), knowledgeIdList, RootEnum.CATEGORY.getValue());

        recentPostDTOS = Optional.ofNullable(operRecordEntities).orElse(new ArrayList<>()).stream().map(item -> {
            RecentPostDTO recentPostDTO = new RecentPostDTO();
            if (Objects.nonNull(item.getOperUser())) {
                recentPostDTO.setOperUserName(item.getOperUser().getUserName()).setOperUser(item.getOperUser().getId());
            }
            recentPostDTO.setOperTime(item.getOperTime()).setOperType(OperTypeEnum.getKey(item.getOperType()));
            if (Objects.nonNull(item.getHandleUser())) {
                recentPostDTO.setHandleUserName(item.getHandleUser().getUserName())
                        .setHandleUser(item.getHandleUser().getId());
            }
            recentPostDTO.setHandleResult(item.getHandleResult()).setObjType(ObjTypeEnum.getKey(item.getObjType()));
            handleOperObj(recentPostDTO, item);
            recentPostDTO.setReserve(item.getReserve());
            return recentPostDTO;
        }).collect(Collectors.toList());
        return recentPostDTOS;
    }

    private void handleOperObj(RecentPostDTO recentPostDTO, OperRecordEntity item) {
        if (Objects.equals(item.getObjType(), ObjTypeEnum.KNOWLEDGE.getValue())) {
            if (!Objects.equals(item.getOperType(), OperTypeEnum.DEL.getValue())) {
                Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(item.getObj());
                knowledgeEntityOp.ifPresent(knowledgeEntity -> recentPostDTO.setObjName(knowledgeEntity.getKName())
                        .setObj(knowledgeEntity.getId()));
            }

        }
        if (Objects.equals(item.getObjType(), ObjTypeEnum.ARTICLE.getValue())) {
            if (!Objects.equals(item.getOperType(), OperTypeEnum.DEL.getValue())) {
                Optional<ArticleEntity> articleEntityOp = articleRepository.findById(item.getObj());
                if (articleEntityOp.isPresent()) {
                    recentPostDTO.setObjName(articleEntityOp.get().getArticleTitle()).setObj(articleEntityOp.get().getId());
                    rootTypeDeal(recentPostDTO, item);
                }
            }
        }
        if (Objects.equals(item.getObjType(), ObjTypeEnum.CATEGORY.getValue())) {
            if (!Objects.equals(item.getOperType(), OperTypeEnum.DEL.getValue())) {
                Optional<CategoryEntity> categoryEntityOp = categoryRepository.findById(item.getObj());
                categoryEntityOp.ifPresent(categoryEntity -> recentPostDTO.setObjName(categoryEntity.getCategoryName())
                        .setObj(categoryEntity.getId()));
            }
        }
        if (Objects.equals(item.getObjType(), ObjTypeEnum.USER.getValue())) {
            Optional<CmsUserEntity> userEntityOp = userRepository.findById(item.getObj());
            if (userEntityOp.isPresent()) {
                recentPostDTO.setObjName(userEntityOp.get().getUserName()).setObj(userEntityOp.get().getId());
                rootTypeDeal(recentPostDTO, item);
            }
        }
    }

    private void rootTypeDeal(RecentPostDTO recentPostDTO, OperRecordEntity item) {
        if (Objects.equals(item.getRootType(), RootEnum.KNOWLEDGE.getValue())) {
            Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(item.getRootObj());
            knowledgeEntityOp.ifPresent(knowledgeEntity -> recentPostDTO.setTargetName(knowledgeEntity.getKName())
                    .setObj(knowledgeEntity.getId()));
        }
    }
}
