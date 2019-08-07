package org.yingzuidou.platform.blog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.catalina.User;
import org.springframework.util.StringUtils;
import org.yingzuidou.platform.common.entity.ArticleEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;
import org.yingzuidou.platform.common.utils.HtmlUtils;
import org.yingzuidou.platform.common.utils.SpringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
     * 文章协作者ID列表
     */
    private String articleParticipantIds;

    /**
     * 文章协作者名字列表
     */
    private String articleParticipantNames;

    /**
     * 参与者列表
     */
    private List<UserDTO> participantList;

    public static Function<Object[], ArticleDTO> recommendKnowledge = data -> new ArticleDTO()
            .setArticleTitle(CmsBeanUtils.objectToString(data[0])).setArticleId(CmsBeanUtils.objectToInt(data[1]))
            .setPostTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[3])).setKnowledgeName(CmsBeanUtils.objectToString(data[4]))
            .setKnowledgeDesc(CmsBeanUtils.objectToString(data[5])).setKnowledgeCoverUrl(String.valueOf(data[6]));

    public static Function<Object[], ArticleDTO> recentArticle = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[2]))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[3]), "yyyy-MM-dd HH:mm:ss"));

    /**
     * 知识库下的文章详细信息
     */

    public static Function<Object[], ArticleDTO> articleList = data -> new ArticleDTO()
            .setArticleId(CmsBeanUtils.objectToInt(data[0])).setArticleTitle(CmsBeanUtils.objectToString(data[1]))
            .setContent(CmsBeanUtils.limitContent(HtmlUtils.delHTMLTag(CmsBeanUtils.objectToString(data[2])), 120))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[3]), "yyyy-MM-dd HH:mm:ss"))
            .setAuthorId(CmsBeanUtils.objectToInt(data[4])).setAuthorName(CmsBeanUtils.objectToString(data[5]))
            .setParticipantNum(CmsBeanUtils.objectToInt(data[6]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[7])).setCategoryName(CmsBeanUtils.objectToString(data[8]))
            .setParticipantList(transformParticipant(CmsBeanUtils.objectToString(data[9]), CmsBeanUtils.objectToString(data[10])))
            .setCoverUrl(CmsBeanUtils.objectToString(data[11]))
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[12])).setKnowledgeName(CmsBeanUtils.objectToString(data[13]));

    /**
     * 将字符串分割的文章参与者的名字和ID转成DTO对象
     *
     * @param participantIds 参与者ID列表
     * @param participantNames 参与者名字列表
     * @return 参与者DTO对象
     */
    private static List<UserDTO> transformParticipant(String participantIds, String participantNames) {
        List<UserDTO> userDTOList = new ArrayList<>();
        if (!StringUtils.hasText(participantIds) || !StringUtils.hasText(participantNames)) {
            return userDTOList;
        }
        String[] participantIdList = participantIds.split(",");
        String[] participantNameList = participantNames.split(",");
        for (int index = 0; index < participantIdList.length; index++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(CmsBeanUtils.objectToInt(participantIdList[index]))
                    .setUserName(CmsBeanUtils.objectToString(participantNameList[index]));
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public static Function<Object[], ArticleDTO> articleShow = data -> new ArticleDTO()
            .setArticleId(Integer.valueOf(String.valueOf(data[0]))).setArticleTitle(String.valueOf(data[1]))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setContent(String.valueOf(data[3])).setAuthorId(CmsBeanUtils.objectToInt(String.valueOf(data[4])))
            .setAuthorName(String.valueOf(data[5])).setKnowledgeId(CmsBeanUtils.objectToInt(data[6]))
            .setKnowledgeName(String.valueOf(data[7])).setCategoryId(CmsBeanUtils.objectToInt(data[8]))
            .setCategoryName(String.valueOf(data[9]))
            .setParticipantList(transformParticipant(CmsBeanUtils.objectToString(data[10]),
                    CmsBeanUtils.objectToString(data[11])));

    public static Function<Object[], UserDTO> participantShow = data -> new UserDTO()
            .setUserId(Integer.valueOf(String.valueOf(data[0]))).setUserName(String.valueOf(data[1]));

    /**
     * 用户最近发布的文章
     */
    public static Function<Object[], ArticleDTO> recentPostList = data -> new ArticleDTO()
            .setArticleId(Integer.valueOf(String.valueOf(data[0]))).setArticleTitle(String.valueOf(data[1]))
            .setContent(CmsBeanUtils.limitContent(HtmlUtils.delHTMLTag(String.valueOf(data[2])), 120))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(data[3]), "yyyy-MM-dd HH:mm:ss"))
            .setCoverUrl(CmsBeanUtils.objectToString(data[4]))
            .setAuthorId(CmsBeanUtils.objectToInt(data[5])).setAuthorName(String.valueOf(data[6]))
            .setKnowledgeId(CmsBeanUtils.objectToInt(data[7])).setKnowledgeName(String.valueOf(data[8]))
            .setCategoryId(CmsBeanUtils.objectToInt(data[9])).setCategoryName(String.valueOf(data[10]));

}
