package org.yingzuidou.platform.blog.dto;


import lombok.Data;
import lombok.experimental.Accessors;

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
public class RecentKnowledgeDTO {

    private String knowledgeName;

    private Integer knowledgeId;

    private String categoryName;

    private Integer categoryId;

    /**
     * 多表查询转换函数,注意需要和原生sql的Fields列表顺序一致
     */
    public static Function<Object[], RecentKnowledgeDTO> function = data -> new RecentKnowledgeDTO()
            .setKnowledgeId(Integer.valueOf(String.valueOf(data[0])))
            .setKnowledgeName(String.valueOf(data[1]))
            .setCategoryId(Integer.valueOf(String.valueOf(data[2])))
            .setCategoryName(String.valueOf(data[3]));
}
