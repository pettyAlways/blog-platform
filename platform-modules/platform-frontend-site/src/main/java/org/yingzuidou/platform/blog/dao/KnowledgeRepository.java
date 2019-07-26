package org.yingzuidou.platform.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;

import java.util.List;

public interface KnowledgeRepository extends PagingAndSortingRepository<KnowledgeEntity, Integer>, QuerydslPredicateExecutor<KnowledgeEntity> {


    /**
     * 查找最近更新的知识库
     *
     * @param isDelete 是否删除
     * @return 知识库列表
     */
    List<KnowledgeEntity> findFirst8ByIsDeleteOrderByEditTimeDesc(String isDelete);

    /**
     * 分页查找分类的知识库
     *
     * @param categoryId 分类ID
     * @param isDelete 是否删除
     * @param pageable 分页信息
     * @return 知识库列表
     */
    List<KnowledgeEntity> findAllByKTypeAndIsDelete(Integer categoryId, String isDelete, Pageable pageable);

    /**
     * 根据知识库ID查找知识库的详情页封面内容
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 知识库详情内容
     */
    @Query(nativeQuery = true, value = "SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k.k_url, k.create_time, " +
            "u.id AS userId, u.user_name, c.id AS categoryId, c.category_name FROM knowledge k " +
            "LEFT JOIN cms_user u ON k.creator = u.id LEFT JOIN category c ON k.k_type = c.id " +
            "WHERE k.id = :knowledgeId AND k.is_delete = :isDelete")
    List<Object[]> findKnowledgeDetail(@Param("knowledgeId") Integer knowledgeId, @Param("isDelete") String isDelete);

    /**
     * 分页查找用户的知识库
     *
     * @param userId 用户iD
     * @param pageable 分页信息
     * @return 知识库列表
     */
    @Query(nativeQuery = true, value = "SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k_url, k.create_time,  c.category_name, user_name, " +
            "(SELECT count(1) FROM article a WHERE a.knowledge_id = k.id AND a.is_delete = 'N') AS articleNums, " +
            "(SELECT count(1) FROM participant p WHERE p.knowledge_id = k.id) AS participantNums " +
            "FROM knowledge k LEFT JOIN category c ON k.k_type = c.id " +
            "LEFT JOIN cms_user u ON k.creator = u.id WHERE k.creator = :userId AND k.is_delete = 'N' #pageable")
    List<Object[]> findUserKnowledgeList(@Param("userId") Integer userId, Pageable pageable);

    /**
     * 用户参与的知识库
     *
     * @param userId 用户id
     * @param pageable 分页信息
     * @return 参与的知识库列表
     */
    @Query(nativeQuery = true, value = "SELECT k.knowledgeId, k.k_name, k.k_desc, k_url, k.create_time, c.category_name, user_name, " +
            "(SELECT count(1) FROM article a WHERE a.knowledge_id = k.knowledgeId AND a.is_delete = 'N') AS articleNums, " +
            "(SELECT count(1) FROM participant p WHERE p.knowledge_id = k.knowledgeId) AS participantNums " +
            "FROM (SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k_url, k.create_time, k.creator, k.k_type " +
            "FROM knowledge k LEFT JOIN participant p ON k.id = p.knowledge_id WHERE p.participant_id = 3 AND k.is_delete = 'N') k " +
            "LEFT JOIN category c ON k.k_type = c.id LEFT JOIN cms_user u ON k.creator = u.id #pageable")
    List<Object[]> findUserParticipantKnowledgeList(Integer userId, Pageable pageable);
}
