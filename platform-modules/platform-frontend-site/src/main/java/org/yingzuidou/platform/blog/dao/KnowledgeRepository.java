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
     * @param access 公开
     * @param isDelete 是否删除
     * @return 知识库列表
     */
    List<KnowledgeEntity> findFirst8ByKAccessAndIsDeleteOrderByEditTimeDesc(String access, String isDelete);

    /**
     * 分页查找分类的知识库
     *
     * @param categoryId 分类ID
     * @param isDelete 是否删除
     * @param pageable 分页信息
     * @return 知识库列表
     */
    @Query(nativeQuery = true, value="SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k_url, k.k_access, k.creator, " +
            "k.create_time, u.user_name, k.k_type, c.category_name, " +
            "(SELECT count(1) FROM article a WHERE a.knowledge_id = k.id AND a.is_delete = 'N' GROUP BY k.id ) articleNum, " +
            "pp.participantNum, pp.participantIds " +
            "FROM knowledge k LEFT JOIN cms_user u ON k.creator = u.id " +
            "LEFT JOIN category c ON k.k_type = c.id LEFT JOIN " +
            "(SELECT p.knowledge_id, count(1) AS participantNum, group_concat(p.participant_id) participantIds " +
            "FROM knowledge kk LEFT JOIN participant p ON p.knowledge_id = kk.id GROUP BY p.knowledge_id) pp " +
            "ON pp.knowledge_id = k.id WHERE k.k_type = :categoryId AND k.is_delete = :isDelete AND k.k_access = '2' ORDER BY k.create_time \n#pageable\n ")
    List<Object[]> findAllByKTypeAndIsDelete(@Param("categoryId") Integer categoryId, @Param("isDelete") String isDelete, Pageable pageable);

    /**
     * 访问指定分类的公开的知识库
     *
     * @param categoryId 分类ID
     * @param isDelete 是否删除
     * @param pageable 分页信息
     * @return 指定分类下的公开知识库
     */
    @Query(nativeQuery = true, value="SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k_url, k.k_access, k.creator, " +
            "k.create_time, u.user_name, k.k_type, c.category_name, " +
            "(SELECT count(1) FROM article a WHERE a.knowledge_id = k.id AND a.is_delete = 'N' GROUP BY k.id ) articleNum, " +
            "pp.participantNum, pp.participantIds " +
            "FROM knowledge k LEFT JOIN cms_user u ON k.creator = u.id " +
            "LEFT JOIN category c ON k.k_type = c.id LEFT JOIN " +
            "(SELECT p.knowledge_id, count(1) AS participantNum, group_concat(p.participant_id) participantIds " +
            "FROM knowledge kk LEFT JOIN participant p ON p.knowledge_id = kk.id GROUP BY p.knowledge_id) pp " +
            "ON pp.knowledge_id = k.id WHERE k.k_type = :categoryId AND k.is_delete = :isDelete AND k.k_access = '2' ORDER BY k.create_time \n#pageable\n ")
    List<Object[]> findCouldAccessKnowledgeByCategory(@Param("categoryId") Integer categoryId, @Param("isDelete") String isDelete, Pageable pageable);
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
    @Query(nativeQuery = true, value = "SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k_url, k.k_access, k.creator, " +
            "k.create_time, u.user_name, k.k_type, c.category_name, " +
            "(SELECT count(1) FROM article a WHERE a.knowledge_id = k.id AND a.is_delete = 'N' GROUP BY k.id ) articleNum, " +
            "pp.participantNum, pp.participantIds " +
            "FROM knowledge k LEFT JOIN cms_user u ON k.creator = u.id " +
            "LEFT JOIN category c ON k.k_type = c.id LEFT JOIN " +
            "(SELECT p.knowledge_id, count(1) AS participantNum, group_concat(p.participant_id) participantIds FROM knowledge kk " +
            "LEFT JOIN participant p ON p.knowledge_id = kk.id GROUP BY p.knowledge_id) pp ON pp.knowledge_id = k.id " +
            "WHERE k.creator = :userId AND k.is_delete = 'N' AND k.k_access <> '1' ORDER BY k.create_time \n#pageable\n")
    List<Object[]> findUserKnowledgeList(@Param("userId") Integer userId, Pageable pageable);

    /**
     * 用户参与的知识库
     *
     * @param userId 用户id
     * @param pageable 分页信息
     * @return 参与的知识库列表
     */
    @Query(nativeQuery = true, value = "SELECT k.knowledgeId, k.k_name, k.k_desc, k_url, k.create_time, k.k_access, " +
            "k.k_type, c.category_name, k.creator, user_name, " +
            "(SELECT count(1) FROM article a WHERE a.knowledge_id = k.knowledgeId AND a.is_delete = 'N') AS articleNums, " +
            "pp.participantNum AS participantNums, pp.participantIds  " +
            "FROM (SELECT k.id AS knowledgeId, k.k_name, k.k_desc, k_url, k.create_time, k.creator, k.k_access, k.k_type " +
            "FROM knowledge k LEFT JOIN participant p ON k.id = p.knowledge_id WHERE p.participant_id = :userId AND k.is_delete = 'N') k " +
            "LEFT JOIN (SELECT p.knowledge_id, count(1) AS participantNum, group_concat(p.participant_id)  AS participantIds " +
            "FROM knowledge kk LEFT JOIN participant p ON p.knowledge_id = kk.id GROUP BY p.knowledge_id) pp " +
            "ON pp.knowledge_id = k.knowledgeId " +
            "LEFT JOIN category c ON k.k_type = c.id " +
            "LEFT JOIN cms_user u ON k.creator = u.id ORDER BY k.create_time \n#pageable\n")
    List<Object[]> findUserParticipantKnowledgeList(@Param("userId") Integer userId, Pageable pageable);

    /**
     * 获取最近的知识库列表
     *
     * @return 最近知识库列表
     */
    @Query(nativeQuery = true, value = "SELECT k.id, k.k_name, k.k_desc, k_url, k.create_time, k.creator, u.user_name, k.k_type, " +
            "c.category_name, aa.articleNums, pp.participantNum FROM knowledge k LEFT JOIN cms_user u ON k.creator = u.id " +
            "LEFT JOIN category c ON k.k_type = c.id " +
            "LEFT JOIN (SELECT a.knowledge_id, count(1) AS articleNums " +
            "FROM article a WHERE a.is_delete = 'N' GROUP BY a.knowledge_id) aa ON aa.knowledge_id = k.id " +
            "LEFT JOIN (SELECT p.knowledge_id, count(1) AS participantNum FROM knowledge kk " +
            "LEFT JOIN participant p ON p.knowledge_id = kk.id GROUP BY p.knowledge_id) pp ON pp.knowledge_id = k.id " +
            "WHERE k.k_access = 2 AND k.is_delete = 'N' ORDER BY k.create_time DESC LIMIT 0, 3")
    List<Object[]> recentKnowledgeInfo();
}
