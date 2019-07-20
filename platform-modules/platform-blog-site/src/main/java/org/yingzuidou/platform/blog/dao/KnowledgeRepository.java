package org.yingzuidou.platform.blog.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;

import java.util.List;

public interface KnowledgeRepository extends PagingAndSortingRepository<KnowledgeEntity, Integer>, QuerydslPredicateExecutor<KnowledgeEntity> {

    List<KnowledgeEntity> findAllByKNameAndIsDelete(String knowledgeName, String isDelete);

    /**
     * 根据是否删除状态查找知识库
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 知识库实体
     */
    KnowledgeEntity findByIdAndIsDelete(@Param("knowledgeId") Integer knowledgeId, @Param("isDelete") String isDelete);

    @Query(nativeQuery = true, value = "SELECT k.id, k.k_name, k.k_desc, k.k_url, u.user_name, k.creator, k.k_access, " +
            "k.k_type, k.k_reserve_o " +
            "FROM knowledge k LEFT JOIN cms_user u ON k.creator = u.id " +
            "WHERE k.id = :knowledgeId AND k.is_delete = :isDelete")
    List<Object[]> findKnowledgeDetail(@Param("knowledgeId") Integer knowledgeId, @Param("isDelete") String isDelete);

    /**
     * 获取用户参与的知识库ID
     *
     * @param userId 用户ID
     * @param isDelete 是否删除
     * @return 参与知识库ID列表
     */
    @Query(nativeQuery = true, value="SELECT DISTINCT knowledgee.id FROM knowledge knowledgee " +
            "LEFT JOIN participant participant ON knowledgee.id = participant.knowledge_id " +
            "WHERE ( knowledgee.creator = :userId OR participant.participant_id = :userId ) " +
            "AND knowledgee.is_delete = :isDelete ")
    List<Integer> findAllKnowledgeIdByKParticipant(@Param("userId") Integer userId, @Param("isDelete") String isDelete);


    /**
     * 用户所参与的知识库用于卡片显示
     *
     * @param userId 用户ID
     * @param isDelete 是否删除
     * @return 知识库列表
     */
    @Query(nativeQuery = true, value="SELECT DISTINCT knowledgee.* FROM knowledge knowledgee " +
            "LEFT JOIN participant participant ON knowledgee.id = participant.knowledge_id " +
            "WHERE ( knowledgee.creator = :userId OR participant.participant_id = :userId ) AND knowledgee.is_delete = :isDelete " +
            "ORDER BY knowledgee.edit_time DESC")
    List<KnowledgeEntity> findAllKnowledgeByKParticipantInAndIsDelete(@Param("userId") Integer userId, @Param("isDelete") String isDelete);


    /**
     * 查找用户所参与的知识库信息用于列表显示
     *
     * @param userId 用户ID
     * @param isDelete 是否删除
     * @return 知识库列表
     */
    @Query(nativeQuery = true, value = "SELECT kk.id, kk.k_name, kk.k_desc, kk.creator, u.user_name, group_concat(uu.user_name), " +
            "group_concat(uu.id) AS partcipantsId " +
            "FROM (SELECT DISTINCT k.id, k.k_name, k.k_desc, k.creator, k.edit_time FROM knowledge k " +
            "LEFT JOIN participant participant ON k.id = participant.knowledge_id WHERE ( k.creator = :userId OR participant.participant_id = :userId ) " +
            "AND k.is_delete = :isDelete ORDER BY k.edit_time DESC) kk " +
            "LEFT JOIN cms_user u ON kk.creator = u.id LEFT JOIN participant p ON kk.id = p.knowledge_id " +
            "LEFT JOIN cms_user uu ON p.participant_id = uu.id GROUP BY kk.id")
    List<Object[]> findKnowledgeByParticipant(@Param("userId") Integer userId, @Param("isDelete") String isDelete);

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
