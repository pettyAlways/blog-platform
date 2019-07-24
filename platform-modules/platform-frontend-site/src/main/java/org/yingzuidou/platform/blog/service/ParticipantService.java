package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.UserDTO;

import java.util.List;

public interface ParticipantService {

    /**
     * 获取知识库下所有的协作人信息
     *
     * @param knowledgeId 知识库ID
     * @return 协作人列表
     */
    List<UserDTO> findAllParticipantInKnowledge(Integer knowledgeId);
}
