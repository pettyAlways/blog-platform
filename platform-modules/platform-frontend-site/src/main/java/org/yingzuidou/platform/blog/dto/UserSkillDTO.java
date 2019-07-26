package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/25
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class UserSkillDTO {

    /**
     * 技能ID
     */
    private Integer skillId;

    /**
     * 技能名字
     */
    private String skillName;

}
