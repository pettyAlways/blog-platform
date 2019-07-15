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
}
