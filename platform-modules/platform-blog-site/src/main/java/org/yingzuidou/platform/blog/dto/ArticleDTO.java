package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.ArticleEntity;

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
public class ArticleDTO extends ArticleEntity {

    private Integer creatorId;

    private String createName;

    private String categoryName;

    private Integer categoryId;

    private String knowledgeName;

    private Integer knowledgeId;

    private Integer knowledgeCreator;

}
