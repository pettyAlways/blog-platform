package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.RecentAritcleDTO;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;

import java.util.List;

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
    void recordCommonOperation(Integer operUser, String operType, String objType, Integer objId, String rootType, Integer root);

    /**
     * 修改知识库和分类的操作记录
     *
     * @param operUser 操作人
     * @param operType 操作类型：1.新增，2.删除，3.修改，4.审核，5.移除，6.申请
     * @param objType 对象类型：1.知识库，2.文章，3.分类，4.用户
     * @param objId 对象主键ID
     * @param reserve 预留字段
     */
    void recordCommonOperation(Integer operUser, String operType, String objType, Integer objId, String reserve);

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
    void recordApplyOperation(Integer operUser, String operType, String objType, Integer objId, String rootType,
                              Integer root, Integer handleUser);

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
    void recordAuditOperation(Integer operUser, String operType, String objType, Integer objId, String rootType,
                              Integer root, String handleResult);

    /**
     * 获取最近的6篇编辑的文章
     *
     * @return 最近编辑列表
     */
    List<RecentAritcleDTO> recentOperArticle();


}
