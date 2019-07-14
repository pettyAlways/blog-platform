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

    KnowledgeDTO item(Integer knowledgeId);
}
