package org.yingzuidou.platform.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.entity.ParticipantEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class KnowledgeDTO {

    /**
     * 知识库ID
     */
    private Integer knowledgeId;

    /**
     * 知识库名字
     */
    private String knowledgeName;

    /**
     * 知识库描述
     */
    private String knowledgeDesc;

    /**
     * 知识库封面
     */
    private String knowledgeCover;

    /**
     * 知识库创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 知识库变更时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date editTime;

    /**
     * 知识库访问方式
     */
    private String access;

    /**
     * 创建人ID
     */
    private Integer creator;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 文章数量
     */
    private Integer articleCounts;

    /**
     * 参与者人数
     */
    private Integer participantCounts;

    /**
     * 最近文章
     */
    private ArticleDTO recentlyArticle;

    /**
     * 协作者ID逗号拼接字符串
     */
    private String participantIds;

    /**
     * 知识库协作人列表
     */
    private List<UserDTO> participantList;

    private List<ArticleDTO> articleList;

    public static Function<Object[], KnowledgeDTO> knowledgeDetail = data -> new KnowledgeDTO()
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeDesc(CmsBeanUtils.objectToString(data[2])).setKnowledgeCover(CmsBeanUtils.objectToString(data[3]))
            .setCreateTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[4]), "yyyy-MM-dd HH:mm:ss"))
            .setCreator(CmsBeanUtils.objectToInt(data[5])).setCreatorName(CmsBeanUtils.objectToString(data[6]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[7])).setCategoryName(CmsBeanUtils.objectToString(data[8]));

    /**
     * 用户创建的知识库列表
     */
    public static Function<Object[], KnowledgeDTO> userKnowledgeList = data -> new KnowledgeDTO()
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeDesc(CmsBeanUtils.limitContent(CmsBeanUtils.objectToString(data[2]), 60))
            .setKnowledgeCover(CmsBeanUtils.objectToString(data[3]))
            .setAccess(CmsBeanUtils.objectToString(data[4]))
            .setCreator(CmsBeanUtils.objectToInt(data[5]))
            .setCreateTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[6]), "yyyy-MM-dd HH:mm:ss"))
            .setCreatorName(CmsBeanUtils.objectToString(data[7]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[8]))
            .setCategoryName(CmsBeanUtils.objectToString(data[9]))
            .setArticleCounts(CmsBeanUtils.objectToInt(data[10]))
            .setParticipantCounts(CmsBeanUtils.objectToInt(data[11]))
            .setParticipantIds(CmsBeanUtils.objectToString(data[12]));

    /**
     * 用户参与的知识库列表
     */
    public static Function<Object[], KnowledgeDTO> userParticipantList = data -> new KnowledgeDTO()
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeDesc(CmsBeanUtils.limitContent(CmsBeanUtils.objectToString(data[2]), 60))
            .setKnowledgeCover(CmsBeanUtils.objectToString(data[3]))
            .setCreateTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[4]), "yyyy-MM-dd HH:mm:ss"))
            .setAccess(CmsBeanUtils.objectToString(data[5]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[6]))
            .setCategoryName(CmsBeanUtils.objectToString(data[7]))
            .setCreator(CmsBeanUtils.objectToInt(data[8]))
            .setCreatorName(CmsBeanUtils.objectToString(data[9]))
            .setArticleCounts(CmsBeanUtils.objectToInt(data[10]))
            .setParticipantCounts(CmsBeanUtils.objectToInt(data[11]))
            .setParticipantIds(CmsBeanUtils.objectToString(data[12]));

    /**
     * 分类下的知识库列表
     */
    public static Function<Object[], KnowledgeDTO> categoryKnowledgeList = data -> new KnowledgeDTO()
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeDesc(CmsBeanUtils.limitContent(CmsBeanUtils.objectToString(data[2]), 26))
            .setKnowledgeCover(CmsBeanUtils.objectToString(data[3]))
            .setAccess(CmsBeanUtils.objectToString(data[4]))
            .setCreator(CmsBeanUtils.objectToInt(data[5]))
            .setCreateTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[6]), "yyyy-MM-dd HH:mm:ss"))
            .setCreatorName(CmsBeanUtils.objectToString(data[7]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[8]))
            .setCategoryName(CmsBeanUtils.objectToString(data[9]))
            .setArticleCounts(CmsBeanUtils.objectToInt(data[10]))
            .setParticipantCounts(CmsBeanUtils.objectToInt(data[11]))
            .setParticipantIds(CmsBeanUtils.objectToString(data[12]));

    /**
     * 分类下的知识库列表
     */
    public static Function<Object[], KnowledgeDTO> relateKnowledgeList = data -> new KnowledgeDTO()
                    .setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(CmsBeanUtils.objectToString(data[1]))
                    .setKnowledgeDesc(CmsBeanUtils.limitContent(CmsBeanUtils.objectToString(data[2]), 60))
                    .setKnowledgeCover(CmsBeanUtils.objectToString(data[3]))
                    .setAccess(CmsBeanUtils.objectToString(data[4]))
                    .setCreator(CmsBeanUtils.objectToInt(data[5]))
                    .setCreateTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[6]), "yyyy-MM-dd HH:mm:ss"))
                    .setCreatorName(CmsBeanUtils.objectToString(data[7]))
                    .setCategoryId(CmsBeanUtils.objectToInt(data[8]))
                    .setCategoryName(CmsBeanUtils.objectToString(data[9]))
                    .setArticleCounts(CmsBeanUtils.objectToInt(data[10]))
                    .setParticipantCounts(CmsBeanUtils.objectToInt(data[11]))
                    .setParticipantIds(CmsBeanUtils.objectToString(data[12]));

    /**
     * 最近知识库列表
     */
    public static Function<Object[], KnowledgeDTO> recentKnowledgeList = data -> new KnowledgeDTO()
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeDesc(CmsBeanUtils.limitContent(CmsBeanUtils.objectToString(data[2]), 60))
            .setKnowledgeCover(CmsBeanUtils.objectToString(data[3]))
            .setCreateTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[4]), "yyyy-MM-dd HH:mm:ss"))
            .setCreator(CmsBeanUtils.objectToInt(data[5]))
            .setCreatorName(CmsBeanUtils.objectToString(data[6]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[7]))
            .setCategoryName(CmsBeanUtils.objectToString(data[8]))
            .setArticleCounts(CmsBeanUtils.objectToInt(data[9]))
            .setParticipantCounts(CmsBeanUtils.objectToInt(data[10]));
}
