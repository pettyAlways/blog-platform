package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.vo.Node;


/**
 * 用户的输出实体
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Data
@Accessors(chain = true)
public class UserDTO {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 资源树
     */
    private Node resourceTree;




}
