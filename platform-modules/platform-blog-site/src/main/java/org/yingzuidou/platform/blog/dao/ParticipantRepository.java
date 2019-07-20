package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
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


    /**
     * 移除指定知识库的一个参与者
     *
     * @param knowledgeId 知识库ID
     * @param participantId 参与者ID
     */
    void removeByKnowledgeIdAndParticipantId(Integer knowledgeId, Integer participantId);

    /**
     * 指定的知识库下是否存在指定的参与者
     *
     * @param knowledgeId 知识库ID
     * @param participantId 参与者ID
     * @return true: 存在 false:不存在
     */
    boolean existsByKnowledgeIdAndParticipantId(Integer knowledgeId, Integer participantId);

    /**
     * 查找知识库下的所有参与者
     *
     * @param knowledgeId 知识库ID
     * @return 指定知识库的参与者
     */
    @Query(nativeQuery = true, value = "SELECT p.participant_id, u.user_name FROM participant p LEFT JOIN cms_user u " +
            "ON p.participant_id = u.id WHERE p.knowledge_id = :knowledgeId ")
    List<Object[]> findAllByKnowledgeId(@Param("knowledgeId") Integer knowledgeId);

    /**
     * 移除一个知识库的所有参与者
     *
     * @param knowledgeId 知识库ID
     */
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM participant p WHERE knowledge_id = :knowledgeId")
    void removeAllParticipantByKnowledgeId(@Param("knowledgeId") Integer knowledgeId);

}
