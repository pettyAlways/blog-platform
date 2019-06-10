package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.cache.CmsCacheManager;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.service.PermissionService;

import java.util.List;
import java.util.Objects;

/**
 * PermissionController
 * 资源管理
 * 资源如果不被启用那么只需要简单不查询资源就可以，没有必要重新加载shiro的资源授权
 * 父资源隐藏那么子资源也会被隐藏
 * @author 鹰嘴豆
 * @date 2018/9/26
 */
@RestController
@RequestMapping(value="/permission")
public class PermissionController {

    private final PermissionService permissionService;

    private final CmsCacheManager cmsCacheManager;

    @Autowired
    public PermissionController(PermissionService permissionService, CmsCacheManager cmsCacheManager) {
        this.permissionService = permissionService;
        this.cmsCacheManager = cmsCacheManager;
    }

    @GetMapping(value="/listPower.do")
    public CmsMap listPower() {
        CmsMap<Node> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.listPower();
        cMap.success().setResult(result.getTree());
        return cMap;
    }

    /**
     * 根据父资源ID获取其下所有的子资源
     *
     * @param permissionDTO 查询条件
     * @param pageInfo 分页条件
     * @return 子资源
     */
    @GetMapping(value="/subPower.do")
    public CmsMap subPower(PermissionDTO permissionDTO, PageInfo pageInfo) {
        CmsMap<List<ResourceEntity>> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.subPower(permissionDTO, pageInfo);
        cMap.success().appendData("total", pageInfo.getCounts()).setResult(result.getResources());
        return cMap;
    }

    /**
     * 删除指定的资源
     *
     * @param ids 待删除的资源ID
     * @return 删除结果
     */
    @DeleteMapping(value="/deletePower.do")
    public CmsMap deletePower(String ids) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
         permissionService.deletePower(ids);
        cMap.success();
        return cMap;
    }

    @PutMapping(value="/updatePower.do")
    public CmsMap updatePower(@RequestBody ResourceEntity entity) {
        permissionService.updateResouce(entity);
        // 更改资源需要清空对应用户的缓存
        List<Integer> userIds = permissionService.findUserIdsByResource(entity.getId());
        if (!userIds.isEmpty()) {
            userIds.forEach(userId -> cmsCacheManager
                    .clearCacheByKeys("resourceCache", "resourceTree_" + userId));
        }
        return CmsMap.ok();
    }

    @PostMapping(value="/savePower.do")
    public CmsMap savePower(@RequestBody ResourceEntity entity) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        permissionService.saveResource(entity);
        cMap.success();
        return cMap;
    }
}
