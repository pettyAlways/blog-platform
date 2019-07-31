package org.yingzuidou.platform.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;

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
     * 文章封面
     */
    private String coverUrl;

    /**
     * 文章发布时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date postTime;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章创建者
     */
    private Integer creatorId;

    /**
     * 创建人名字
     */
    private String createName;

    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 知识库名字
     */
    private String knowledgeName;

    private List<UserDTO> participantList;

    /**
     * 知识库ID
     */
    private Integer knowledgeId;

    public static Function<Object[], ArticleDTO> articleListInKnowledge = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setPostTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setCreatorId(CmsBeanUtils.objectToInt(data[3])).setCreateName(CmsBeanUtils.objectToString(data[4]));

    public static Function<Object[], ArticleDTO> articleShow = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setPostTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setContent(CmsBeanUtils.objectToString(data[3])).setCreatorId(CmsBeanUtils.objectToInt(String.valueOf(data[4])))
            .setCreateName(CmsBeanUtils.objectToString(data[5])).setKnowledgeId(CmsBeanUtils.objectToInt(data[6]))
            .setKnowledgeName(CmsBeanUtils.objectToString(data[7])).setCategoryId(CmsBeanUtils.objectToInt(data[8]))
            .setCategoryName(CmsBeanUtils.objectToString(data[9])).setCoverUrl(CmsBeanUtils.objectToString(data[10]));

    public static Function<Object[], ArticleDTO> knowledgeCardShow = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setPostTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[2]), "yyyy-MM-dd HH:mm:ss"));

}
