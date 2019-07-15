package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/12
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class RecentArticleDTO {

    private String articleTitle;

    private Integer articleId;

    private String knowledgeName;

    private Integer knowledgeId;

    private String categoryName;

    private Integer categoryId;

    private Date postTime;

    public static Function<Object[], RecentArticleDTO> transform = datas -> new RecentArticleDTO()
            .setArticleId(Integer.valueOf(String.valueOf(datas[0])))
            .setArticleTitle(String.valueOf(datas[1]))
            .setKnowledgeId(Integer.valueOf(String.valueOf(datas[2])))
            .setKnowledgeName(String.valueOf(datas[3]))
            .setCategoryId(Integer.valueOf(String.valueOf(datas[4])))
            .setCategoryName(String.valueOf(datas[5]))
            .setPostTime(DateUtil.transformStrToDate(String.valueOf(datas[6]), "yyyy-MM-dd HH:mm:ss"));

}
