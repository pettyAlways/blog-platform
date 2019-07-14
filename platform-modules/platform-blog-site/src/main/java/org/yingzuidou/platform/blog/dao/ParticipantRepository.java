package org.yingzuidou.platform.blog.dao;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.platform.common.entity.ParticipantEntity;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/8
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ParticipantRepository extends PagingAndSortingRepository<ParticipantEntity, Integer>, QuerydslPredicateExecutor<ParticipantEntity> {


    void removeAllByKnowledgeIdAndParticipantId(Integer knowledgeId, Integer participantId);

    boolean existsByKnowledgeIdAndParticipantId(Integer knowledgeId, Integer participantId);

    @EntityGraph(value = "Participant.Graph", type = EntityGraph.EntityGraphType.FETCH)
    List<ParticipantEntity> findAllByKnowledgeId(Integer knowledgeId);


}
