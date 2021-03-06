package org.yingzuidou.platform.blog.service;

import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.paging.PageInfo;

import java.util.List;

/**
 * 类功能描述
 * 知识库模块f服务
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface KnowledgeService {


    /**
     * 获取推荐的知识库列表
     *
     * @return 推荐知识库列表
     */
    List<KnowledgeDTO> listRecommend();

    /**
     * 获取最近的知识库
     *
     * @return 最近知识库列表
     */
    List<KnowledgeDTO> listRecent();

    /**
     * 分页查找分类下的知识库
     *
     * @param categoryId 分类ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    List<KnowledgeDTO> listByCategory(Integer categoryId, PageInfo pageInfo);

    /**
     * 知识库详情
     *
     * @param knowledgeId 知识库ID
     * @param token 加密的知识库需要token验证访问
     * @param userId 访问用户
     * @return 知识库详情内容
     */
    KnowledgeDTO retrieveKnowledgeDetail(Integer knowledgeId, String token, Integer userId);

    /**
     * 用户创建的知识库
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    List<KnowledgeDTO> retrieveUserKnowledge(Integer userId, PageInfo pageInfo);

    /**
     * 获取用户参与的知识库
     *
     * @param userId 用户ID
     * @param pageInfo 分页信息
     * @return 参与的知识库列表
     */
    List<KnowledgeDTO> retrieveUserParticipant(Integer userId, PageInfo pageInfo);

    /**
     * 获取知识库创建人的信息
     *
     * @param knowledgeId 知识库ID
     * @return 创建人信息
     */
    UserDTO retrieveKnowledgeCreator(Integer knowledgeId);


    /**
     * 获取知识库的协作人信息列表
     *
     * @param knowledgeId 知识库ID
     * @return 协作人ID
     */
    List<UserDTO> retrieveKnowledgeParticipant(Integer knowledgeId);

    /**
     * 加入知识库
     *
     * @param knowledgeId 知识库ID
     * @param reason 加入原因
     */
    void joinKnowledge(Integer knowledgeId, String reason);

    /**
     * 是否已经加入知识库
     *
     * @param knowledgeId 知识库ID
     * @return true 已经加入 false 还未加入
     */
    boolean alreadyJoinKnowledge(Integer knowledgeId);

    /**
     * 知识库密码校验
     *
     * @param knowledgeId 知识库ID
     * @param password 输入的密码
     * @return true 通过 false 不通过
     */
    String passwordVerify(Integer knowledgeId, String password);

    /**
     * 查找分页下可访问的知识库
     *
     * @param categoryId 分类ID
     * @param pageInfo 分页信息
     * @return 知识库列表
     */
    List<KnowledgeDTO> listCouldAccessKnowledgeByCategory(Integer categoryId, PageInfo pageInfo);

    /**
     * 获取最近知识库详细信息列表
     *
     * @return 详细信息列表
     */
    List<KnowledgeDTO> listRecentInfo();
}
