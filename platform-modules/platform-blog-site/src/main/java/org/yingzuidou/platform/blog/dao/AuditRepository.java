package org.yingzuidou.platform.blog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.common.entity.AuditEntity;

import java.util.List;

public interface AuditRepository extends PagingAndSortingRepository<AuditEntity, Integer>, QuerydslPredicateExecutor<AuditEntity> {

    /**
     * 获取作者申请列表
     *
     * @param handleResult 处理结果
     * @param pageable 分页内容
     * @return 作者申请列表
     */
    @Query(nativeQuery = true, value = "SELECT au.user_name AS applyUser, hu.user_name AS handlerUser, " +
            "a.apply_time, a.reason, a.handle_result, a.handle_time, a.reject_reason, a.id AS auditId " +
            "FROM audit a LEFT JOIN cms_user au ON a.apply_user = au.id " +
            "LEFT JOIN cms_user hu ON a.handle_user = hu.id " +
            "where a.handle_result = :handleResult AND a.apply_type = '1' AND a.handle_user = :handleUser " +
            "ORDER BY a.handle_result, a.apply_time DESC \n#pageable\n")
    List<Object[]> findBeAuthorAudit(@Param("handleUser") Integer handleUser, @Param("handleResult") String handleResult, Pageable pageable);

    /**
     * 获取加入知识库的申请列表
     *
     * @param handleResult 处理结果
     * @param pageable 分页信息
     * @return 知识库加入申请列表
     */
    @Query(nativeQuery = true, value = "SELECT au.user_name AS applyUser, hu.user_name AS handleUser, " +
            "k.k_name, a.apply_time, a.handle_result, a.handle_time, a.reason, a.reject_reason, a.id AS auditId  " +
            "FROM audit a LEFT JOIN cms_user au ON au.id = a.apply_user " +
            "LEFT JOIN cms_user hu ON hu.id = a.handle_user " +
            "LEFT JOIN knowledge k ON k.id = a.apply_obj " +
            "where a.handle_result = :handleResult AND a.apply_type = '2' And a.handle_user = :handleUser " +
            "ORDER BY a.handle_result, a.apply_time DESC \n#pageable\n")
    List<Object[]> findKnowledgeJoinList(@Param("handleUser") Integer handleUser, @Param("handleResult") String handleResult, Pageable pageable);
}
