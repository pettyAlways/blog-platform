package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.ArticleRepository;
import org.yingzuidou.platform.blog.dao.OperRecordRepository;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.blog.service.RecentEditService;
import org.yingzuidou.platform.common.entity.*;

import java.util.*;

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
    public void recordCommonOperation(CmsUserEntity operUser, String operType, String objType, Integer objId,
                                      String rootType, Integer root) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser).setOperTime(new Date()).setOperType(operType).setObjType(objType)
                .setObj(objId).setRootType(rootType).setRootObj(root);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public void recordCommonOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType, String reserve) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser).setOperTime(new Date()).setOperType(operType).setObjType(objType)
                .setObj(objId).setRootType(rootType).setReserve(reserve);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public void recordCommonOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType, Integer root, String reserve) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser).setOperTime(new Date()).setOperType(operType).setObjType(objType)
                .setObj(objId).setRootType(rootType).setRootObj(root).setReserve(reserve);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public void recordApplyOperation(CmsUserEntity operUser, String operType, String objType, Integer objId,
                                     String rootType, Integer root, CmsUserEntity handleUser) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser).setOperTime(new Date()).setOperType(operType).setObjType(objType)
                .setObj(objId).setRootType(rootType).setRootObj(root).setHandleUser(handleUser);
        operRecordRepository.save(operRecordEntity);
    }

    @Override
    public void recordAuditOperation(CmsUserEntity operUser, String operType, String objType, Integer objId,
                                     String rootType, Integer root, String handleResult) {
        OperRecordEntity operRecordEntity = new OperRecordEntity();
        operRecordEntity.setOperUser(operUser).setOperTime(new Date()).setOperType(operType).setObjType(objType)
                .setObj(objId).setRootType(rootType).setRootObj(root).setHandleResult(handleResult);
        operRecordRepository.save(operRecordEntity);
    }
}
