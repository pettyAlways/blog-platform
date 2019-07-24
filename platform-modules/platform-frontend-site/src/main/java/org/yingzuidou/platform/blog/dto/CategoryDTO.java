package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.CategoryEntity;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/4
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class CategoryDTO extends CategoryEntity {

    private Integer categoryId;

    private String categoryName;

}
