package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.ParticipantEntity;

import java.util.List;

public interface ParticipantRepository extends PagingAndSortingRepository<ParticipantEntity, Integer>, QuerydslPredicateExecutor<ParticipantEntity> {


    /**
     * 查找知识库下的所有协作人
     *
     * @param knowledgeId 知识库ID
     * @return 协作人列表
     */
    @Query(nativeQuery = true, value = "SELECT u.id, u.user_name FROM participant p LEFT JOIN cms_user u " +
            "ON p.participant_id = u.id WHERE p.knowledge_id = :knowledgeId")
    List<Object[]> findAllParticipantInKnowledge(@Param("knowledgeId") Integer knowledgeId);
}
