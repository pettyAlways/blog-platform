package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.service.UserService;
import org.yingzuidou.platform.common.dto.CmsMap;
import org.yingzuidou.platform.common.paging.PageInfo;

/**
 * 用户管理控制器
 * 如果用户重新授予角色那么需要重新加载shiro的资源授权
 *
 * @author 鹰嘴豆
 * @date 2018/9/13
 */

@RestController
@RequestMapping(value="/user")
public class UserController {

    private final UserService userService;

    private final ShiroService shiroService;

    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    public UserController(UserService userService, ShiroService shiroService,
                          ShiroFilterFactoryBean shiroFilterFactoryBean) {
        this.userService = userService;
        this.shiroService = shiroService;
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
    }

    @GetMapping(value="/list.do")
    public CmsMap<UserDTO> list(UserDTO userDTO, PageInfo pageInfo) {
        CmsMap<UserDTO> cMap = new CmsMap<>();
        UserDTO result = userService.list(userDTO, pageInfo);
        cMap.success().appendData("counts", pageInfo.getCounts()).setResult(result);
        return cMap;
    }


    @PostMapping(value="/save.do")
    public CmsMap save(@RequestBody CmsUserEntity cmsUserEntity) {
        userService.save(cmsUserEntity);
        return CmsMap.ok();
    }

    @PutMapping(value="/edit.do")
    public CmsMap edit(@RequestBody CmsUserEntity cmsUserEntity) {
        userService.update(cmsUserEntity);
        return CmsMap.ok();
    }

    @DeleteMapping(value="/delete.do")
    public CmsMap delete(@RequestBody Integer[] delIds) {
        userService.delete(delIds);
        return CmsMap.ok();
    }

    @GetMapping("/userInfo.do")
    public CmsMap<UserDTO> userInfo() {
        CmsMap<UserDTO> cmsMap = new CmsMap<>();
        UserDTO userDTO = userService.userInfo();
        return cmsMap.success().appendData("currentUser", CmsCommonUtil.getCurrentLoginUser())
                .setResult(userDTO);
    }

    /**
     * 给角色授予角色并且重新加载shiro的授权资源
     *
     * @param userDTO 用户授角信息
     * @return 请求状态
     */
    @PostMapping("/authUser.do")
    public CmsMap authUser(@RequestBody UserDTO userDTO) {
        userService.authUser(userDTO);
        shiroService.updatePermission(shiroFilterFactoryBean);
        return CmsMap.ok();
    }

    @GetMapping("/acquireRoles.do")
    public CmsMap acquireRoles(Integer id) {
        UserDTO userDTO = userService.acquireRoles(id);
        return CmsMap.ok().setResult(userDTO.getRoles());
    }
}
