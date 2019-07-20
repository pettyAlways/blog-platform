package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

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
public class RecentPostDTO {

    private String operUserName;

    private Integer operUser;

    private Date operTime;

    private String operType;

    private String handleUserName;

    private Integer handleUser;

    private String handleResult;

    private String objType;

    private String objName;

    private Integer obj;

    private String reserve;


    /**
     * 在移除知识库用户时所指向的知识库ID
     */
    private Integer target;

}
