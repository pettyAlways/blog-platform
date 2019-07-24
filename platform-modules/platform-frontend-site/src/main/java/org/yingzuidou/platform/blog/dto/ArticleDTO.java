package org.yingzuidou.platform.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.catalina.User;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;
import org.yingzuidou.platform.common.utils.HtmlUtils;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class ArticleDTO {

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章封面
     */
    private String coverUrl;

    /**
     * 文章发布时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 作者名字
     */
    private String authorName;

    /**
     * 协作人数量
     */
    private Integer participantNum;

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
     * 知识库封面URL路径
     */
    private String knowledgeCoverUrl;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 参与者列表
     */
    private List<UserDTO> participantList;

    public static Function<Object[], ArticleDTO> recommendKnowledge = data -> new ArticleDTO()
            .setArticleTitle(String.valueOf(data[0])).setArticleId(Integer.valueOf(String.valueOf(data[1])))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setKnowledgeId(Integer.valueOf(String.valueOf(data[3]))).setKnowledgeName(String.valueOf(data[4]))
            .setKnowledgeDesc(String.valueOf(data[5])).setKnowledgeCoverUrl(String.valueOf(data[6]));

    public static Function<Object[], ArticleDTO> recentArticle = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[2]))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[3]), "yyyy-MM-dd HH:mm:ss"));

    /**
     * 知识库下的文章详细信息
     */
    public static Function<Object[], ArticleDTO> articleList = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setContent(CmsBeanUtils.limitContent(HtmlUtils.delHTMLTag(CmsBeanUtils.objectToString(data[2])), 200))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[3]), "yyyy-MM-dd HH:mm:ss"))
            .setAuthorId(CmsBeanUtils.objectToInt(data[4])).setAuthorName(CmsBeanUtils.objectToString(data[5]))
            .setParticipantNum(CmsBeanUtils.objectToInt(data[6]));

    public static Function<Object[], ArticleDTO> articleShow = data -> new ArticleDTO()
            .setArticleId(Integer.valueOf(String.valueOf(data[0]))).setArticleTitle(String.valueOf(data[1]))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setContent(String.valueOf(data[3])).setAuthorId(CmsBeanUtils.objectToInt(String.valueOf(data[4])))
            .setAuthorName(String.valueOf(data[5])).setKnowledgeId(CmsBeanUtils.objectToInt(data[6]))
            .setKnowledgeName(String.valueOf(data[7])).setCategoryId(CmsBeanUtils.objectToInt(data[8]))
            .setCategoryName(String.valueOf(data[9]));

    public static Function<Object[], UserDTO> participantShow = data -> new UserDTO()
            .setUserId(Integer.valueOf(String.valueOf(data[0]))).setUserName(String.valueOf(data[1]));

}
