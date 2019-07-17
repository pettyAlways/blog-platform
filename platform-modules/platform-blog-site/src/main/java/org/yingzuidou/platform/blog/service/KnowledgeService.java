package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;

import java.util.List;

/**
 * 类功能描述
 * 知识库模块f服务
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface KnowledgeService {

    void addKnowledge(KnowledgeEntity knowledgeEntity);

    List<KnowledgeDTO> list(KnowledgeDTO knowledgeDTO);


    /**
     * 移除指定参与者
     *
     * @param uId 参与者ID
     * @param kId 知识库ID
     */
    void removeParticipant(Integer uId, Integer kId);

    /**
     * 共享移除指定参与者，拥有共享移除参与者权限用户调用
     *
     * @param userId 参与者ID
     * @param knowledgeId 知识库ID
     */
    void removeShareParticipant(Integer userId, Integer knowledgeId);

    /**
     * 获取单个知识库的基本信息以及知识库参与者信息
     *
     * @param knowledgeId 知识库ID
     * @return 知识库信息对象
     */
    KnowledgeDTO item(Integer knowledgeId);

    /**
     * 移除指定的知识库
     *
     * @param knowledgeId 知识库ID
     */
    void removeKnowledge(Integer knowledgeId);

    /**
     * 拥有共享移除知识库权限的操作者调用无条件移除知识库
     *
     * @param knowledgeId 知识库ID
     */
    void removeShareKnowledge(Integer knowledgeId);

    /**
     * 更新知识库，只有知识库拥有者才有权限更新知识库
     *
     * @param knowledgeEntity 知识库内容
     */
    void editKnowledge(KnowledgeEntity knowledgeEntity);

    /**
     * 共享更新知识库，拥有共享更新知识库权限用户无权限更新知识库
     *
     * @param knowledgeEntity 知识库内容
     */
    void editShareKnowledge(KnowledgeEntity knowledgeEntity);

    /**
     * 获取当前操作用户拥有的知识库，包括参与的知识库
     *
     * @return 所拥有的知识库列表
     */
    List<KnowledgeDTO> retrieveParticipantKnowledge();

    /**
     * 分类是否还被其他知识库引用
     *
     * @param categoryId 分类ID
     * @return 是否还被其他知识库引用
     */
    boolean stillUsingCategory(Integer categoryId);
}
