package org.yingzuidou.platform.blog.dto;

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
public class CategoryDTO extends CategoryEntity {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
