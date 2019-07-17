package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;

import java.util.List;

public interface KnowledgeRepository extends PagingAndSortingRepository<KnowledgeEntity, Integer>, QuerydslPredicateExecutor<KnowledgeEntity> {

    List<KnowledgeEntity> findAllByKNameAndIsDelete(String knowledgeName, String isDelete);

    KnowledgeEntity findByIdAndIsDelete(Integer knowledgeId, String isDelete);

    @Query(nativeQuery = true, value="SELECT knowledgee.id FROM knowledge knowledgee LEFT JOIN participant participant " +
            "ON knowledgee.id = participant.knowledge_id WHERE (knowledgee.creator = :userId OR participant.participant_id in (:userId))" +
            "AND knowledgee.is_delete = :isDelete " +
            "UNION " +
            "SELECT knowledge.id FROM knowledge knowledge JOIN participant participant ON knowledge.id = participant.knowledge_id " +
            "WHERE participant.participant_id IN (:userId) AND knowledge.is_delete = :isDelete")
    List<Integer> findAllByKParticipantInAndIsDelete(@Param("userId") Integer userId, @Param("isDelete") String isDelete);


    @Query(nativeQuery = true, value="SELECT DISTINCT knowledgee.* FROM knowledge knowledgee " +
            "LEFT JOIN participant participant ON knowledgee.id = participant.knowledge_id " +
            "WHERE ( knowledgee.creator = :userId OR participant.participant_id = :userId ) AND knowledgee.is_delete = :isDelete " +
            "ORDER BY knowledgee.edit_time DESC")
    List<KnowledgeEntity> findAllKnowledgeByKParticipantInAndIsDelete(@Param("userId") Integer userId, @Param("isDelete") String isDelete);

    /**
     * 分类是否还被其他知识库引用
     *
     * @param kType 分类ID
     * @param isDelete 是否删除
     * @return 是否被其他知识库引用
     */
    @Query(nativeQuery = true, value = "SELECT count(*) FROM knowledge k where  k.k_type = :kType AND k.is_delete = :isDelete")
    Integer findAllByKTypeAndIsDelete(@Param("kType") Integer kType, @Param("isDelete") String isDelete);
}
