package org.yingzuidou.platform.blog.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.yingzuidou.platform.blog.dto.ArticleDTO;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.ArticleParticipantEntity;
import org.yingzuidou.platform.common.paging.PageInfo;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Integer>, QuerydslPredicateExecutor<ArticleEntity> {

    /**
     * 查找最多文章的前3个知识库并找到每个知识库下按发布时间的升序的前5篇文章
     * <note>使用mysql用户变量需要转义用户变量的冒号</note>
     *
     * @return 前3个知识库及前5篇文章的集合
     */
    @Query(nativeQuery = true, value = "SELECT aa.article_title, aa.id as articleId, aa.post_time, aa.knowledge_id, aa.k_name, aa.k_desc, aa.k_url FROM ( " +
            "SELECT ar.article_title, ar.id, ar.post_time, kk.knowledge_id, kk.k_name, kk.k_desc, kk.k_url, IF(@number=ar.knowledge_id,@temp\\:=@temp+1,@temp\\:=1) AS number , @number\\:=ar.knowledge_id FROM article ar INNER JOIN " +
            "(SELECT k.id as knowledge_id, k.k_name, k.k_desc, k.k_url from (SELECT COUNT(a.id) num, a.knowledge_id FROM article a WHERE a.is_delete = 'N' GROUP BY a.knowledge_id ORDER BY num DESC LIMIT 0,3) aa INNER JOIN knowledge k ON k.id = aa.knowledge_id) kk " +
            "ON ar.knowledge_id = kk.knowledge_id WHERE ar.is_delete = 'N' ORDER BY ar.post_time ASC) aa WHERE aa.number <= 5")
    List<Object[]> findMostArticleKnowledgeLimit3();

    /**
     * 最近文章列表
     *
     * @return 最近10篇文章
     */
    @Query(nativeQuery = true, value = "SELECT a.id, a.article_title, a.knowledge_id, a.post_time FROM article a WHERE  a.is_delete = 'N' ORDER BY  a.post_time " +
            "LIMIT 0, 8")
    List<Object[]> recentArticle();

    /**
     * 知识库下最近发布的文章
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 最近一篇文章
     */
    ArticleEntity findFirstByKnowledgeIdAndIsDeleteOrderByPostTime(Integer knowledgeId, String isDelete);

    /**
     * 知识库下的文章数
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 知识库下的文章数
     */
    Integer countByKnowledgeIdAndIsDelete(Integer knowledgeId, String isDelete);

    /**
     * 分页获取知识库下的文章列表
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @param pageable 分页信息
     * @return 文章列表
     */
    @Query(nativeQuery = true, value = "SELECT a.id AS articleId, a.article_title, a.content, a.post_time, " +
            "u.id as authorId, u.user_name AS authorName, app.nums AS participantNums FROM article a " +
            "LEFT JOIN cms_user u ON a.author_id = u.id " +
            "LEFT JOIN (SELECT ap.article_id, count(ap.id) nums FROM article_participant ap GROUP BY ap.article_id) app " +
            "ON a.id = app.article_id WHERE a.knowledge_id = :knowledgeId AND a.is_delete = :isDelete \n#pageable\n")
    List<Object[]> findArticleListInKnowledge(@Param("knowledgeId") Integer knowledgeId,
                                                   @Param("isDelete") String isDelete,
                                                   Pageable pageable);

    /**
     * 获取文章显示的信息
     *
     * @param articleId 文章ID
     * @param isDelete 是否删除
     * @return 文章显示信息
     */
    @Query(nativeQuery = true, value = "SELECT a.id as articleId, a.article_title, a.post_time, a.content, a.creator, " +
            "u.user_name, k.id AS knowledgeId, k.k_name, c.id AS categoryId, c.category_name " +
            "FROM article a LEFT JOIN cms_user u  ON a.creator = u.id " +
            "LEFT JOIN knowledge k ON k.id = a.knowledge_id " +
            "LEFT JOIN category c ON c.id = k.k_type WHERE a.id = :articleId AND a.is_delete = :isDelete")
    List<Object[]>  findArticleShowInfo(@Param("articleId") Integer articleId, @Param("isDelete") String isDelete);

    /**
     * 分页查找知识库下的文章列表
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @param pageable 分页信息
     * @return 文章列表
     */
    List<ArticleEntity> findAllByKnowledgeIdAndIsDelete(Integer knowledgeId, String isDelete, Pageable pageable);

    /**
     * 获取知识库下最近发布的文章
     *
     * @param knowledgeId 知识库ID
     * @param isDelete 是否删除
     * @return 最近文章列表
     */
    List<ArticleEntity> findFirst6ByKnowledgeIdAndIsDeleteOrderByPostTimeDesc(Integer knowledgeId, String isDelete);

    /**
     * 文章下的所有参与者列表
     *
     * @param articleId 文章ID
     * @return 参与者列表
     */
    @Query(nativeQuery = true, value = "SELECT u.id, u.user_name FROM article_participant ap INNER JOIN cms_user u ON ap.user_id = u.id " +
            "WHERE ap.article_id = :articleId ")
    List<Object[]> findParticipantInArticle(@Param("articleId") Integer articleId);

    /**
     * 获取用户最近发布的文章
     *
     * @param authorId 用户ID
     * @param pageable 分页信息
     * @return 文章列表
     */
    @Query(nativeQuery = true, value = "SELECT a.id AS articleId, a.article_title, a.content,	a.post_time, a.cover_url, " +
            "u.id AS authorId, u.user_name, k.id AS knowledgeId, k.k_name, c.id AS categoryId, c.category_name " +
            "FROM article a LEFT JOIN knowledge k ON a.knowledge_id = k.id " +
            "LEFT JOIN category c ON k.k_type = c.id LEFT JOIN cms_user u ON a.author_id = u.id " +
            "WHERE a.author_id = :authorId AND a.is_delete = 'N' #pageable")
    List<Object[]> findArticleUserRecentPost(@Param("authorId") Integer authorId, Pageable pageable);

}
