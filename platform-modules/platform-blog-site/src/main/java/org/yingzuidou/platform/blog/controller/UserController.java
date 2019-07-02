package org.yingzuidou.platform.blog.controller;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.UserRoleService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/loadUser/{userName}")
    public CmsMap loadUserByUserName(@PathVariable String userName) {
        CmsUserEntity cmsUserEntity = userService.loadUserByUserName(userName);
        List<String> roleNameList =  userRoleService.retrieveRolesByUserId(cmsUserEntity.getId());
        return CmsMap.ok().appendData("roleList", roleNameList).setResult(cmsUserEntity);
    }

    /**
     * 获取当前用户的可访问资源
     *
     * @return 资源列表
     */
    @GetMapping("userInfo.do")
    public CmsMap<UserDTO> userInfo() {
        UserDTO userDTO = userService.userInfo();
        return CmsMap.<UserDTO>ok().setResult(userDTO);
    }

}
