package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.common.entity.CmsUserEntity;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface OperRecordService {

    /**
     * 常用的操作日志记录方法
     *
     * @param operUser 操作人
     * @param operType 操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请
     * @param objType 对象类型：1.知识库，2.文章，3.分类，4.用户
     * @param objId 对象主键ID
     * @param rootType 根类型：1.知识库
     * @param root 根对象ID
     */
    void recordCommonOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType, Integer root);

    /**
     * 修改知识库和分类的操作记录
     *
     * @param operUser 操作人
     * @param operType 操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请
     * @param objType 对象类型：1.知识库，2.文章，3.分类，4.用户
     * @param objId 对象主键ID
     * @param reserve 预留字段
     */
    void recordCommonOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType, String reserve);

    /**
     * 修改知识库和分类的操作记录
     *
     * @param operUser 操作人
     * @param operType 操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请
     * @param objType 对象类型：1.知识库，2.文章，3.分类，4.用户
     * @param objId 对象主键ID
     * @param rootType 根类型
     * @param root 根对象
     * @param reserve 预留字段
     */
    void recordCommonOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType, Integer root, String reserve);

    /**
     * 申请操作日志记录方法
     *
     * @param operUser 操作人
     * @param operType 操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请
     * @param objType 对象类型：1.知识库，2.文章，3.分类，4.用户
     * @param objId 对象主键ID
     * @param rootType 根类型：1.知识库
     * @param root 根对象ID
     * @param handleUser 处理人
     */
    void recordApplyOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType,
                              Integer root, CmsUserEntity handleUser);

     /**
     * 审核操作日志记录方法
     *
     * @param operUser 操作人
     * @param operType 操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请
     * @param objType 对象类型：1.知识库，2.文章，3.分类，4.用户
     * @param objId 对象主键ID
     * @param rootType 根类型：1.知识库
     * @param root 根对象ID
     * @param handleResult 处理结果
     */
    void recordAuditOperation(CmsUserEntity operUser, String operType, String objType, Integer objId, String rootType,
                              Integer root, String handleResult);
}
