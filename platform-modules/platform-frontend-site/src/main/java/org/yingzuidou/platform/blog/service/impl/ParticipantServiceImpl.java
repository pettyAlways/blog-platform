package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.platform.blog.dao.KnowledgeRepository;
import org.yingzuidou.platform.blog.dao.ParticipantRepository;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.ParticipantService;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.exception.BusinessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private KnowledgeRepository knowledgeRepository;

    /**
     * 知识库下的协作人列表
     *
     * @param knowledgeId 知识库ID
     * @return 协作人列表
     */
    @Override
    public List<UserDTO> findAllParticipantInKnowledge(Integer knowledgeId) {
        Optional<KnowledgeEntity> knowledgeEntityOp = knowledgeRepository.findById(knowledgeId);
        if (!knowledgeEntityOp.isPresent()) {
            throw new BusinessException("知识库不存在");
        }
        List<Object[]> data = participantRepository.findAllParticipantInKnowledge(knowledgeId);
        if (!data.isEmpty()) {
            return data.stream().map(item -> UserDTO.participantDetail.apply(item)).collect(Collectors.toList());
        }
        return null;
    }
}
