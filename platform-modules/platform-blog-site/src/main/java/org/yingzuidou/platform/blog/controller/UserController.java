package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.dto.UserDTO;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.blog.service.SysConstService;
import org.yingzuidou.platform.blog.service.UserRoleService;
import org.yingzuidou.platform.blog.service.UserService;
import org.yingzuidou.platform.common.constant.ConstEnum;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.MessageEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Autowired
    private SysConstService sysConstService;

    @RequestMapping("/loadUser/{userName}")
    public CmsMap loadUserByUserName(@PathVariable String userName) {
        List<String> roleNameList = new ArrayList<>();
        CmsUserEntity cmsUserEntity = userService.loadUserByUserName(userName);
        if (Objects.nonNull(cmsUserEntity)) {
            roleNameList =  userRoleService.retrieveRolesByUserId(cmsUserEntity.getId());
        }

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
        Map<String, String> sysConst = sysConstService.listSystemConst(ConstEnum.CONST.getValue());
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        return CmsMap.<UserDTO>ok().appendData("sysConst", sysConst).appendData("curUser", user).setResult(userDTO);
    }
}
