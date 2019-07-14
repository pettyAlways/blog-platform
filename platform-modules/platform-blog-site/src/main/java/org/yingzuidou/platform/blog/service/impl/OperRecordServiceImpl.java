package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dao.ArticleRepository;
import org.yingzuidou.platform.blog.dao.OperRecordRepository;
import org.yingzuidou.platform.blog.dto.RecentAritcleDTO;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.blog.service.RecentEditService;
import org.yingzuidou.platform.common.constant.ObjTypeEnum;
import org.yingzuidou.platform.common.constant.OperTypeEnum;
import org.yingzuidou.platform.common.entity.*;

import java.util.*;
import java.util.function.Function;
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
public class OperRecordServiceImpl implements OperRecordService {

    @Autowired
    private OperRecordRepository operRecordRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RecentEditService recentEditService;

    @Override
    public void recordCommonOperation(Integer operUser, String operType, String objType, Integer objId,
                                      String rootType, Integer root) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser);
        operRecordEntity.setOperTime(new Date());
        operRecordEntity.setOperType(operType);
        operRecordEntity.setObjType(objType);
        operRecordEntity.setObj(objId);
        operRecordEntity.setRootType(rootType);
        operRecordEntity.setRootObj(root);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public void recordCommonOperation(Integer operUser, String operType, String objType, Integer objId, String reserve) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser);
        operRecordEntity.setOperTime(new Date());
        operRecordEntity.setOperType(operType);
        operRecordEntity.setObjType(objType);
        operRecordEntity.setObj(objId);
        operRecordEntity.setReserve(reserve);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public void recordApplyOperation(Integer operUser, String operType, String objType, Integer objId,
                                     String rootType, Integer root, Integer handleUser) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser);
        operRecordEntity.setOperTime(new Date());
        operRecordEntity.setOperType(operType);
        operRecordEntity.setObjType(objType);
        operRecordEntity.setObj(objId);
        operRecordEntity.setRootType(rootType);
        operRecordEntity.setRootObj(root);
        operRecordEntity.setHandleUser(handleUser);
        operRecordRepository.save(operRecordEntity);

    }

    @Override
    public void recordAuditOperation(Integer operUser, String operType, String objType, Integer objId,
                                     String rootType, Integer root, String handleResult) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser);
        operRecordEntity.setOperTime(new Date());
        operRecordEntity.setOperType(operType);
        operRecordEntity.setObjType(objType);
        operRecordEntity.setObj(objId);
        operRecordEntity.setRootType(rootType);
        operRecordEntity.setRootObj(root);
        operRecordEntity.setHandleResult(handleResult);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public List<RecentAritcleDTO> recentOperArticle() {
        List<RecentAritcleDTO> recentArticleDTOList = new ArrayList<>();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        List<RecentEditEntity> recentEditEntities = recentEditService.listRecentArticle(user.getId());
        List<ArticleEntity> articleEntities = Optional.ofNullable(recentEditEntities).orElse(new ArrayList<>()).stream()
                .map(RecentEditEntity::getArticle).collect(Collectors.toList());
        if (!articleEntities.isEmpty()) {
           recentArticleDTOList = articleEntities.stream().map(item -> {
              RecentAritcleDTO recentAritcleDTO = new RecentAritcleDTO();
              recentAritcleDTO.setArticleId(item.getId());
              recentAritcleDTO.setArticleTitle(item.getArticleTitle());
              recentAritcleDTO.setKnowledgeId(item.getKnowledge().getId());
              recentAritcleDTO.setKnowledgeName(item.getKnowledge().getkName());
              recentAritcleDTO.setCategoryId(item.getKnowledge().getkType().getId());
              recentAritcleDTO.setCategoryName(item.getKnowledge().getkType().getCategoryName());
              recentAritcleDTO.setPostTime(item.getPostTime());
              return recentAritcleDTO;
            }).collect(Collectors.toList());

        }
        return recentArticleDTOList;
    }
}
