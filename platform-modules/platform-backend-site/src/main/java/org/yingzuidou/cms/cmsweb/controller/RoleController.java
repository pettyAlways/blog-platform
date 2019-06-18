package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.cache.CmsCacheManager;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;
import org.yingzuidou.cms.cmsweb.service.RoleService;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;
import java.util.Objects;

/**
 * 角色管理相关接口
 * 角色如果启用和禁用互相转化那么需要重新加载shiro的资源授权
 * 因为角色变化会影响用户的当前授权资源所以需要清空相关用户缓存
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private CmsCacheManager cmsCacheManager;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @GetMapping("/list.do")
    public CmsMap list(RoleDTO roleDTO, PageInfo pageInfo) {
        CmsMap<List<RoleEntity>> result = new CmsMap<>();
        roleService.pagingRole(roleDTO, pageInfo);
        result.success().appendData("counts", pageInfo.getCounts()).setResult(roleDTO.getRoles());
        return result;
    }

    @GetMapping("/listAll.do")
    public CmsMap listAll() {
        RoleDTO roleDTO = roleService.listAll();
        return CmsMap.ok().setResult(roleDTO.getRoles());
    }

    @PostMapping("/save.do")
    public CmsMap save(@RequestBody RoleEntity roleEntity) {
        roleService.save(roleEntity);
        return CmsMap.ok();
    }

    /**
     * 更新角色信息
     * 如果角色启用禁用发生变化那么需要重新加载shiro授权信息
     * 因为角色变化会影响用户的当前授权资源所以需要清空相关用户缓存
     *
     * @param roleEntity 角色信息
     * @return 请求状态
     */
    @PutMapping("/edit.do")
    public CmsMap edit(@RequestBody RoleEntity roleEntity) {
        boolean reload = roleService.edit(roleEntity);
        // 转换启用状态时重新进行资源授权
        if (reload) {
            shiroService.updatePermission(shiroFilterFactoryBean);
        }
        // 清空当前角色的所有用户的缓存
        List<Integer> userIds = roleService.findUserIdsByRole(roleEntity.getId());
        if (Objects.nonNull(userIds)) {
            userIds.forEach(userId -> cmsCacheManager
                    .clearCacheByKeys("resourceCache", "resourceTree_" + userId));
        }
        return CmsMap.ok();
    }

    @DeleteMapping("/delete.do")
    public CmsMap delete(String ids) {
        roleService.delete(ids);
        return CmsMap.ok();
    }

    /**
     * 更新当前用户的被授权的资源，动态更新shiro中的已缓存的
     * 旧授权资源，清空缓存在resourceCache中以resourceTree_用户id
     * 为key的资源
     *
     * @param roleDTO 新授权的资源
     * @return 返回状态
     */
    @PostMapping("/resourceAuth.do")
    public CmsMap resourceAuth(@RequestBody RoleDTO roleDTO) {
        roleService.resourceAuth(roleDTO);
        shiroService.updatePermission(shiroFilterFactoryBean);
        return CmsMap.ok();
    }

    @GetMapping("/acquireResource.do")
    public CmsMap acquireResource(Integer id) {
        RoleDTO roleDTO = roleService.acquireResource(id);
        CmsMap<List<Integer>> result = new CmsMap<>();
        return result.success().setResult(roleDTO.getResources());
    }
}
